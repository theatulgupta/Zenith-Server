package com.agkminds.zenith.services.User;

import com.agkminds.zenith.exceptions.UserException;
import com.agkminds.zenith.models.User;

import java.util.List;

public interface UserService {

    User findUserById(Integer id) throws UserException;

    User findUserByEmail(String email) throws UserException;

    void toggleFollowUser(Integer followerId, Integer userId, boolean follow) throws UserException;

    void updateUser(User user, Integer id) throws UserException;

    List<User> getAllUsers() throws UserException;

    List<User> searchUser(String query) throws UserException;

    User findUserByJwt(String jwt) throws UserException;
}