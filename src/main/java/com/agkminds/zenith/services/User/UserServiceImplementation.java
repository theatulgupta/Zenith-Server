package com.agkminds.zenith.services.User;

import com.agkminds.zenith.config.JwtProvider;
import com.agkminds.zenith.exceptions.UserException;
import com.agkminds.zenith.models.User;
import com.agkminds.zenith.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImplementation implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<User> getAllUsers() throws UserException {
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            throw new UserException("No users found.");
        }
        return users;
    }

    @Override
    public User findUserById(Integer id) throws UserException {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserException("User not found with id: " + id));
    }

    @Override
    public User findUserByEmail(String email) throws UserException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UserException("User not found with email: " + email);
        }
        return user;
    }

    @Override
    @Transactional
    public void updateUser(User user, Integer id) throws UserException {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new UserException("User not found with id: " + id));

        if (user.getFullName() != null && !user.getFullName().isEmpty()) {
            existingUser.setFullName(user.getFullName());
        }
        if (user.getEmail() != null && !user.getEmail().isEmpty()) {
            existingUser.setEmail(user.getEmail());
        }
        if (user.getGender() != null) {
            existingUser.setGender(user.getGender());
        }

        userRepository.save(existingUser);
    }

    @Override
    @Transactional
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