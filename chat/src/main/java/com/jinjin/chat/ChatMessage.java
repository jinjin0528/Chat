package com.jinjin.chat;

import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "chat")
@Getter
@Setter
@ToString
public class ChatMessage {
    @Id
    private String chatId;
    private String content;
    private String sender;
    private LocalDateTime sentAt;
    private String chatRoomId;


}
