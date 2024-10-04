package com.agkminds.zenith.controllers;

import com.agkminds.zenith.exceptions.UserException;
import com.agkminds.zenith.models.Chat;
import com.agkminds.zenith.models.User;
import com.agkminds.zenith.services.Chat.ChatService;
import com.agkminds.zenith.services.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/chat")
public class ChatController {

    private final ChatService chatService;
    private final UserService userService;

    @Autowired
    public ChatController(ChatService chatService, UserService userService) {
        this.chatService = chatService;
        this.userService = userService;
    }

    private User getUserFromJwt(String jwt) throws UserException {
        return userService.findUserByJwt(jwt);
    }

    @PostMapping("/create")
    public Chat createChat(@RequestHeader("Authorization") String jwt, @RequestParam Integer userId) throws UserException {
        User reqUser = getUserFromJwt(jwt);
        User chatWith = userService.findUserById(userId);
        return chatService.createChat(reqUser, chatWith);
    }

    @GetMapping
    public List<Chat> getUsersChat(@RequestHeader("Authorization") String jwt) throws UserException {
        User user = getUserFromJwt(jwt);
        return chatService.findChatsByUserId(user.getId());
    }
}