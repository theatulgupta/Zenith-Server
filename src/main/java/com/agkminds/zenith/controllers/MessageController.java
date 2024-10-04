package com.agkminds.zenith.controllers;

import com.agkminds.zenith.exceptions.ChatException;
import com.agkminds.zenith.exceptions.UserException;
import com.agkminds.zenith.models.Message;
import com.agkminds.zenith.models.User;
import com.agkminds.zenith.services.Chat.ChatService;
import com.agkminds.zenith.services.Message.MessageService;
import com.agkminds.zenith.services.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/message")
public class MessageController {


    private final UserService userService;

    private final MessageService messageService;

    @Autowired
    public MessageController(ChatService chatService, UserService userService, MessageService messageService) {
        this.userService = userService;
        this.messageService = messageService;
    }

    @PostMapping("/chat/{chatId}")
    public ResponseEntity<Message> createMessage(@RequestBody Message message, @PathVariable Integer chatId, @RequestHeader("Authorization") String jwt) throws UserException, ChatException {

        User user = userService.findUserByJwt(jwt);
        Message createdMessage = messageService.createMessage(user, chatId, message);
        return ResponseEntity.ok(createdMessage);
    }

    @GetMapping("/chat/{chatId}")
    public ResponseEntity<List<Message>> getMessagesByChatId(@PathVariable Integer chatId, @RequestHeader("Authorization") String jwt) throws UserException, ChatException {

        User user = userService.findUserByJwt(jwt);
        return ResponseEntity.ok(messageService.getMessagesByChatId(chatId));
    }
}