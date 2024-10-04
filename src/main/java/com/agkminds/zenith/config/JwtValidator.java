package com.agkminds.zenith.config;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

public class JwtValidator extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        String jwt = request.getHeader(JwtConstant.JWT_HEADER);

        String email = Optional.ofNullable(jwt).map(JwtProvider::getEmailFromJwtToken).orElse(null);

        if (email != null) {
            try {
                Authentication authentication = new UsernamePasswordAuthenticationToken(email, null, Collections.emptyList());

                SecurityContextHolder.getContext().setAuthentication(authentication);

            } catch (JwtException e) {
                throw new BadCredentialsException("Invalid token: " + e.getMessage());
            }
        }

        filterChain.doFilter(request, response);
    }
}