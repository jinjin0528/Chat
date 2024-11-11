package com.jinjin.chat.repository;

import com.jinjin.chat.entity.ChatMessage;
import org.apache.catalina.LifecycleState;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository extends MongoRepository<ChatMessage, String> {
    List<ChatMessage> searchByContentContainingIgnoreCase(String keyword);
    ChatMessage save(ChatMessage chatMessage);
}
