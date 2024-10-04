package com.agkminds.zenith.services.Chat;

import com.agkminds.zenith.exceptions.ChatException;
import com.agkminds.zenith.models.Chat;
import com.agkminds.zenith.models.User;

import java.util.List;

public interface ChatService {

    Chat createChat(User reqUser, User chatWith);

    Chat findChatById(Integer chatId) throws ChatException;

    List<Chat> findChatsByUserId(Integer userId);
}
