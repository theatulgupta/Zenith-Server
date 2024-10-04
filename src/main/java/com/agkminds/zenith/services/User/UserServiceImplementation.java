package com.agkminds.zenith.services.User;

import com.agkminds.zenith.config.JwtProvider;
import com.agkminds.zenith.exceptions.UserException;
import com.agkminds.zenith.models.User;
import com.agkminds.zenith.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImplementation implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public List<User> getAllUsers() throws UserException {
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            throw new UserException("No users found.");
        }

        users.forEach(user -> user.setPassword(null));
        return users;
    }

    @Override
    public User findUserById(Integer id) throws UserException {
        return userRepository.findById(id)
                .map(user -> {
                    user.setPassword(null);
                    return user;
                })
                .orElseThrow(() -> new UserException("User not found with id: " + id));
    }

    @Override
    public User findUserByEmail(String email) throws UserException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UserException("User not found with email: " + email);
        }
        user.setPassword(null);
        return user;
    }

    @Override
    public void updateUser(User user, Integer id) throws UserException {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new UserException("User not found with id: " + id));

        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            throw new UserException("Email is required.");
        }

        existingUser.setFullName(user.getFullName());
        existingUser.setEmail(user.getEmail());
        existingUser.setGender(user.getGender());

        User newUser = userRepository.save(existingUser);
        newUser.setPassword(null);
    }

    @Override
    public void toggleFollowUser(Integer reqUserId, Integer userToFollowId, boolean follow) throws UserException {
        User reqUser = findUserById(reqUserId);
        User userToFollow = findUserById(userToFollowId);

        if (follow) {
            if (userToFollow.getFollowers().contains(reqUserId)) {
                throw new UserException("User is already followed by the reqUserId: " + reqUserId);
            }
            userToFollow.getFollowers().add(reqUserId);
            reqUser.getFollowings().add(userToFollowId);
        } else {
            if (!userToFollow.getFollowers().contains(reqUserId)) {
                throw new UserException("User is not followed by the reqUserId: " + reqUserId);
            }
            userToFollow.getFollowers().remove(reqUserId);
            reqUser.getFollowings().remove(userToFollowId);
        }

        userRepository.save(userToFollow);
        userRepository.save(reqUser);
    }

    @Override
    public List<User> searchUser(String query) throws UserException {
        if (query == null || query.trim().isEmpty()) {
            throw new UserException("Query string cannot be null or empty.");
        }

        List<User> users = userRepository.searchUser(query.trim());

        if (users.isEmpty()) {
            throw new UserException("No users found matching the query: " + query);
        }
        users.forEach(user -> user.setPassword(null));
        return users;
    }

    @Override
    public User findUserByJwt(String jwt) throws UserException {
        String email = JwtProvider.getEmailFromJwtToken(jwt);

        if (email == null) {
            throw new UserException("Invalid JWT token.");
        }
        return findUserByEmail(email);
    }
}