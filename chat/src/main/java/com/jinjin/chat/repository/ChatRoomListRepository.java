package com.jinjin.chat.repository;

import com.jinjin.chat.entity.ChatRoom;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRoomListRepository extends MongoRepository<ChatRoom, String> {
    List<ChatRoom> findAll();
    List<ChatRoom> searchByKeywordContainingIgnoreCase(String keyword);
    ChatRoom findChatRoomByChatRoomId(String chatRoomId);
}
