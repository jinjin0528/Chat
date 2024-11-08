package com.jinjin.chat;

import com.jinjin.chat.entity.ChatMessage;
import com.jinjin.user.User;
import com.jinjin.user.UserRepository;
import com.jinjin.user.UserStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ChatService {
    private static  final Logger log = LoggerFactory.getLogger(ChatService.class);

//    연결된 사용자의 상태를 관리하는 맵
    private final Map<String, String> userStatus = new ConcurrentHashMap<>();

//    메세지 저장할 데이터베이스 레포
    private final ChatRepository chatRepository;
    private final UserRepository userRepository;

    public ChatService(ChatRepository chatRepository, UserRepository userRepository) {
        this.chatRepository = chatRepository;
        this.userRepository =  userRepository;
    }

    public void handleUserConnect(String sessionId, String username) {
        User user = userRepository.findByUserName(username);
        if(user != null) {
            user.setStatus(UserStatus.online);
            user.setLastActiveTime(System.currentTimeMillis());
            userRepository.save(user);
        }
    }

    public void handleUserDisconnect(String sessionId) {
        String username = userStatus.remove(sessionId);
        if(username != null) {
            log.info("사용자 연결 해제 : {} (Session Id: {})", username, sessionId);
        }
    }

    public ChatMessage saveChat(String sender, String content) {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setSender(sender);
        chatMessage.setContent(content);
        chatMessage.setSentAt(LocalDateTime.now());
        return chatRepository.save(chatMessage);
    }

    public List<ChatMessage> getChatMessagesBySender(String sender) {
        return chatRepository.findBySender(sender);
    }

    public List<ChatMessage> getChatRoomByChatRoomId(String chatRoomId) {
        return chatRepository.findByChatRoomId(chatRoomId);
    }
}
