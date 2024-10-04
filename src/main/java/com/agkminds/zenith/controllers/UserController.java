package com.agkminds.zenith.controllers;

import com.agkminds.zenith.exceptions.UserException;
import com.agkminds.zenith.models.User;
import com.agkminds.zenith.services.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/{id}")
    public User findUserById(@PathVariable("id") Integer id) throws UserException {
        return userService.findUserById(id);
    }

    @GetMapping("/users/email/{email}")
    public User findUserByEmail(@PathVariable("email") String email) throws UserException {
        return userService.findUserByEmail(email);
    }

    @PutMapping("/users/update")
    public String updateUser(@RequestHeader("Authorization") String jwt) throws UserException {
        User user = userService.findUserByJwt(jwt);
        userService.updateUser(user, user.getId());
        return "User Updated Successfully";
    }

    @PutMapping("/users/toggle-follow/{userId}")
    public String toggleFollowUser(
            @RequestHeader("Authorization") String jwt,
            @PathVariable("userId") Integer userId,
            @RequestParam("follow") boolean follow) throws UserException {
        User reqUser = userService.findUserByJwt(jwt);
        userService.toggleFollowUser(reqUser.getId(), userId, follow);
        return follow ? "Following" : "Unfollowed";
    }

    @GetMapping("/users/search")
    public List<User> searchUser(@RequestParam("query") String query) throws UserException {
        return userService.searchUser(query);
    }

    @GetMapping("/users")
    public List<User> getAllUsers() throws UserException {
        return userService.getAllUsers();
    }

    @GetMapping("/users/profile")
    public User getUserFromToken(@RequestHeader("Authorization") String jwt) throws UserException {
        return userService.findUserByJwt(jwt);
    }
}