package com.jinjin.chat.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
imp편의점 아침ort java.util.List;

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
    private LocalDateTime createdAt;
    private String keyword;
}
