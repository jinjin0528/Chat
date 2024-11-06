package com.jinjin.chat;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat")
public class ChatController {

    private final ChatService chatService;
    private final SimpMessagingTemplate simpMessagingTemplate;

//    @MessageMapping("/message/{chatRoomId}")
//    public ChatMessage sendMessage(ChatMessage message){
//        return message;
//    }

    @MessageMapping("message/{chatRoomId}")
    public void sendMessage(ChatMessage message, @DestinationVariable String chatRoomId) {
        try {
            LocalDateTime sendAt = chatService.saveChat(message.getSender(), message.getContent());
            message.setSentAt(sendAt);
            simpMessagingTemplate.convertAndSendToUser("/sub/" + chatRoomId, message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
