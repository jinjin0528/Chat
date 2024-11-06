package com.jinjin.chat;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ChatService {
    private final ChatRepository chatRepository;

    public ChatService(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    public ChatMessage saveChat(String sender, String content) {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setSender(sender);
        chatMessage.setContent(content);
        chatMessage.setSentAt(LocalDateTime.now());
        return chatRepository.save(chatMessage);
    }

    public List<ChatMessage> getChatMessagesBySender(String sender) {
        return chatRepository.findBySender(sender);
    }

    public List<ChatMessage> getChatRoomByChatRoomId(String chatRoomId) {
        return chatRepository.findByChatRoomId(chatRoomId);
    }
}
