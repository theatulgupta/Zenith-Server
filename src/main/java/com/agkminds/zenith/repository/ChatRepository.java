package com.agkminds.zenith.repository;

import com.agkminds.zenith.models.Chat;
import com.agkminds.zenith.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, Integer> {
    List<Chat> findByUsersId(Integer userId);

    @Query("SELECT c FROM Chat c WHERE :user MEMBER OF c.users AND :reqUser MEMBER OF c.users")
    Chat findChatByUsersId(@Param("user") User user, @Param("reqUser") User reqUser);
}
