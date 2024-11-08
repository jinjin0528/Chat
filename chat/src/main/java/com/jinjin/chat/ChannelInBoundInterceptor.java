package com.jinjin.chat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;


public class ChannelInBoundInterceptor implements ChannelInterceptor {

    private static final Logger log = LoggerFactory.getLogger(ChannelInBoundInterceptor.class);
    private final ChatService chatService;

    public ChannelInBoundInterceptor(ChatService chatService) {
        this.chatService = chatService;
    }

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        log.info("command = {}", accessor.getCommand());
        log.info("destination = {}", accessor.getDestination());
        log.info("message = {}",accessor.getMessage());

        handleMessage(accessor.getCommand(), accessor);

        log.info("Handle 완료");
        return message;
    }

    // 메세지 처리 메서드. 사용자와 연결 확인
    public void handleMessage(StompCommand command, StompHeaderAccessor accessor) {
        if (command == StompCommand.CONNECT) {
            String sessionId = accessor.getSessionId();
            String username = accessor.getFirstNativeHeader("username");
            log.info("사용자가 연결되었습니다. : {}", accessor.getSessionId());
            chatService.handleUserConnect(sessionId, username);
        } else if(command == StompCommand.DISCONNECT) {
            log.info("사용자와 연결 햬제되었습니다. : {}", accessor.getSessionId());
            chatService.handleUserDisconnect(accessor.getSessionId());
        }
    }
}
