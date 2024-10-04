package com.agkminds.zenith.repository;

import com.agkminds.zenith.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.fullName LIKE %:query% OR u.email LIKE %:query%")
    List<User> searchUser(String query);
}
