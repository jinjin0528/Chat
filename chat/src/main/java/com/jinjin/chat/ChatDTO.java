package com.jinjin.chat;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ChatDTO {

    private String messageId;
    private String message;
    private String senderName;
    private String senderProfilePhoto;
    private String receiverName;
    private String receiverProfilePhoto;
}
