package com.jinjin.chat;

import com.jinjin.chat.entity.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/chat")
public class ChatController {

    private final ChatService chatService;
    private final SimpMessagingTemplate simpMessagingTemplate;

//    @CrossOrigin
//    @MessageMapping("/message/{chatRoomId}")
//    public ChatMessage sendMessage(ChatMessage message){
//        return message;
//    }

    @MessageMapping("message/{chatRoomId}")
    public void sendMessage(ChatMessage message, @DestinationVariable String chatRoomId) {
        try {
            LocalDateTime sendAt = chatService.saveChat(message.getSender(), message.getContent());
            message.setSentAt(sendAt);
            simpMessagingTemplate.convertAndSendToUser("/topic/" + chatRoomId, message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
