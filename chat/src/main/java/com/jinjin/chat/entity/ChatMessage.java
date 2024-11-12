package com.jinjin.chat.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "chat")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {
    @Id
    private String chatId;
    private String chatRoomId;
    private String content;
    private String senderId;
    private String receiverId;
    private String fileUrl;
    private LocalDateTime sentAt;
    private String keyword;
}
