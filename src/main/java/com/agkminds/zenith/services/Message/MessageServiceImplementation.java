package com.agkminds.zenith.services.Message;

import com.agkminds.zenith.exceptions.ChatException;
import com.agkminds.zenith.models.Chat;
import com.agkminds.zenith.models.Message;
import com.agkminds.zenith.models.User;
import com.agkminds.zenith.repository.ChatRepository;
import com.agkminds.zenith.repository.MessageRepository;
import com.agkminds.zenith.services.Chat.ChatService;
import com.agkminds.zenith.services.User.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageServiceImplementation implements MessageService {

    private final ChatService chatService;
    private final UserService userService;
    private final MessageRepository messageRepository;
    private final ChatRepository chatRepository;

    @Override
    @Transactional
    public Message createMessage(User user, Integer chatId, Message message) throws ChatException {
        Chat chat = chatService.findChatById(chatId);
        if (chat == null) throw new ChatException("Chat not found with chatId " + chatId);

        message.setChat(chat);
        message.setUser(user);
        message.setTimestamp(LocalDateTime.now());

        Message savedMessage = messageRepository.save(message);

        chat.getMessages().add(savedMessage);
        chatRepository.save(chat);

        return savedMessage;
    }

    @Override
    public List<Message> getMessagesByChatId(Integer chatId) throws ChatException {
        Chat chat = chatService.findChatById(chatId);
        if (chat == null) throw new ChatException("Chat not found with chatId " + chatId);

        return messageRepository.findByChatId(chatId);
    }


}