package com.jinjin.chat;

import com.jinjin.chat.entity.ChatMessage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ChatRepository extends MongoRepository<ChatMessage, String> {
    List<ChatMessage> findByChatRoomId(String chatRoomId);
    List<ChatMessage> findBySender(String sender);
}
