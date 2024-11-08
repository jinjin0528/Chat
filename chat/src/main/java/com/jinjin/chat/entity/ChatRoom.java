package com.jinjin.chat.entity;

import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "chatroom")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoom {
    @Id
    private String id;
    private String chatRoomId;
    private String creatorId;
    private List<String> membersIds;
    private long createdAt;
}
