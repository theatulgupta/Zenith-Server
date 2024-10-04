package com.agkminds.zenith.controllers;

import com.agkminds.zenith.config.JwtProvider;
import com.agkminds.zenith.exceptions.BadRequestException;
import com.agkminds.zenith.models.User;
import com.agkminds.zenith.repository.UserRepository;
import com.agkminds.zenith.response.AuthResponse;
import com.agkminds.zenith.services.User.CustomUserDetailsService;
import com.agkminds.zenith.services.User.UserService;
import com.agkminds.zenith.utils.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/signup")
    public AuthResponse registerUser(@RequestBody User user) {
        User existingUser = userRepository.findByEmail(user.getEmail());

        if (existingUser != null) {
            throw new BadRequestException("User already exists with email: " + user.getEmail());
        }

        User newUser = new User();
        user.setFullName(user.getFullName());
        user.setEmail(user.getEmail());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setGender(user.getGender());

        User savedUser = userRepository.save(user);

        Authentication authentication = new UsernamePasswordAuthenticationToken(savedUser.getEmail(), savedUser.getPassword());

        String token = JwtProvider.generateToken(authentication);

        return new AuthResponse("Registered Successfully", token);
    }

    @PostMapping("/login")
    public AuthResponse registerUser(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticate(loginRequest.getEmail(), loginRequest.getPassword());

        String token = JwtProvider.generateToken(authentication);

        return new AuthResponse("Login Successful", token);
    }

    private Authentication authenticate(String email, String password) {
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);

        if (userDetails == null) {
            throw new BadCredentialsException("Invalid email");
        }

        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid Password");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}

