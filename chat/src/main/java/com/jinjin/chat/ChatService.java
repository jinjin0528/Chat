package com.jinjin.chat;

import com.jinjin.chat.entity.ChatMessage;
import com.jinjin.chat.entity.ChatRoom;
import com.jinjin.chat.repository.ChatMessageRepository;
import com.jinjin.chat.repository.ChatRoomListRepository;
import com.jinjin.user.User;
import com.jinjin.user.UserRepository;
import com.jinjin.user.UserStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.DispatcherServlet;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.Optional;

@Service
public class ChatService {
    private static  final Logger log = LoggerFactory.getLogger(ChatService.class);

//    연결된 사용자의 상태를 관리하는 맵
    private final Map<String, String> userStatus = new ConcurrentHashMap<>();

//    메세지 저장할 데이터베이스 레포
    private final ChatRoomListRepository chatRoomListRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final UserRepository userRepository;
    private final DispatcherServlet dispatcherServlet;

    @Autowired
    public ChatService(ChatRoomListRepository chatRoomListRepository,
                       ChatMessageRepository chatMessageRepository,
                       UserRepository userRepository, DispatcherServlet dispatcherServlet) {
        this.chatRoomListRepository = chatRoomListRepository;
        this.chatMessageRepository = chatMessageRepository;
        this.userRepository = userRepository;
        this.dispatcherServlet = dispatcherServlet;
    }

    public void handleUserConnect(String sessionId, String username) {
        User user = userRepository.findByUsername(username);
        if(user != null) {
            user.setStatus(UserStatus.online);
            user.setLastActiveTime(System.currentTimeMillis());
            userRepository.save(user);
        }
    }

    public void handleUserDisconnect(String sessionId) {
        String username = userStatus.remove(sessionId);
        if(username != null) {
            User user = userRepository.findByUsername ( username );
            if (user != null) {
                user.setStatus ( UserStatus.offline );
                user.setLastActiveTime ( System.currentTimeMillis () );
                userRepository.save ( user );
            }
            log.info("사용자 연결 해제 : {} (Session Id: {})", username, sessionId);
        }
    }

    // 채팅방 저장
    public ChatMessage saveChat(String sender, String content) {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setSender(sender);
        chatMessage.setContent(content);
        chatMessage.setSentAt(LocalDateTime.now());
        return chatMessageRepository.save(chatMessage);
    }

    // 특정 채팅방 조회
    public ChatRoom getChatRoomByChatRoomId(String chatRoomId) {
        return chatRoomListRepository.findChatRoomByChatRoomId(chatRoomId);
    }

    // 채팅방 목록 조회
    public List<ChatRoom> findAllChatRooms() {
        return chatRoomListRepository.findAll();
    }

    // 키워드(채팅방 이름, 채팅방 내용으로 검색)
    List<ChatRoom> searchChatRoomAndMessage(String keyword) {
        List<ChatRoom> roomsByKeyword = chatRoomListRepository.searchByKeywordContainingIgnoreCase(keyword);
        List<ChatMessage> messagesByContent = chatMessageRepository.searchByContentContainingIgnoreCase(keyword);

        List<ChatRoom> roomsByMessageContent = messagesByContent.stream()
                .map(message -> chatRoomListRepository.findChatRoomByChatRoomId(message.getChatRoomId()))
                .filter(room -> room != null)  // null 체크
                .collect(Collectors.toList());
//                .map(message -> Optional.ofNullable(chatRoomListRepository.findChatRoomByChatRoomId(message.getChatRoomId())))
//                .filter(Optional::isPresent) // Optional이 비어 있지 않은 경우에만 필터링
//                .map(Optional::get)           // Optional 값을 추출하여 Stream에 추가
//                .collect(Collectors.toList());

        return Stream.concat(roomsByKeyword.stream(), roomsByMessageContent.stream())
                .distinct()
                .collect(Collectors.toList());
    }
}
