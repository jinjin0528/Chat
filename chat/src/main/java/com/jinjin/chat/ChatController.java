package com.jinjin.chat;

import com.jinjin.chat.entity.ChatMessage;
import com.jinjin.chat.entity.ChatRoom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private final ChatService chatService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    public ChatController(ChatService chatService, SimpMessagingTemplate simpMessagingTemplate) {
        this.chatService = chatService;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

//    @CrossOrigin
    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(ChatMessage message){
        return chatService.saveChat(message.getSender(), message.getContent());
    }

    @GetMapping("/rooms")
    public List<ChatRoom> findAll() {
        return chatService.findAllChatRooms();
    }

    @GetMapping("/rooms/{chatRoomId}")
    public ChatRoom findChatRoomByChatRoomId(@PathVariable String chatRoomId) {
        return chatService.getChatRoomByChatRoomId(chatRoomId);
    }

    @GetMapping("/rooms/search")
    public List<ChatRoom> search(@RequestParam String keyword) {
        return chatService.searchChatRoomAndMessage(keyword);
    }
//
//    @MessageMapping("message/{chatRoomId}")
//    public void sendMessage(ChatMessage message, @DestinationVariable String chatRoomId) {
//        try {
//            LocalDateTime sendAt = chatService.saveChat(message.getSender(), message.getContent());
//            message.setSentAt(sendAt);
//            simpMessagingTemplate.convertAndSendToUser("/topic/" + chatRoomId, message);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
