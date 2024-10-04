package com.agkminds.zenith.services.Message;

import com.agkminds.zenith.exceptions.ChatException;
import com.agkminds.zenith.models.Message;
import com.agkminds.zenith.models.User;

import java.util.List;

public interface MessageService {
    Message createMessage(User user, Integer chatId, Message message) throws ChatException;

    List<Message> getMessagesByChatId(Integer chatId) throws ChatException;
}
