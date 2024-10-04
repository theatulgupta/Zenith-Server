package com.agkminds.zenith.services.Chat;

import com.agkminds.zenith.exceptions.ChatException;
import com.agkminds.zenith.models.Chat;
import com.agkminds.zenith.models.User;
import com.agkminds.zenith.repository.ChatRepository;
import com.agkminds.zenith.services.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ChatServiceImplementation implements ChatService {

    @Autowired
    private UserService userService;

    @Autowired
    private ChatRepository chatRepository;

    @Override
    public Chat createChat(User reqUser, User chatWith) {
        Chat existingChat = chatRepository.findChatByUsersId(reqUser, chatWith);
        if (existingChat != null) {
            return existingChat;
        }

        existingChat = chatRepository.findChatByUsersId(chatWith, reqUser);
        if (existingChat != null) {
            return existingChat;
        }

        Chat newChat = new Chat();
        newChat.getUsers().add(reqUser);
        newChat.getUsers().add(chatWith);
        newChat.setTimeStamp(LocalDateTime.now());
        return chatRepository.save(newChat);
    }

    @Override
    public Chat findChatById(Integer chatId) throws ChatException {
        return chatRepository.findById(chatId).orElseThrow(() -> new ChatException("Chat not found with chatId " + chatId));
    }

    @Override
    public List<Chat> findChatsByUserId(Integer userId) {
        return chatRepository.findByUsersId(userId);
    }
}