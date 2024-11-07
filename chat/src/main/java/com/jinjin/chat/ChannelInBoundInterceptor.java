package com.jinjin.chat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;

import static org.springframework.core.io.support.SpringFactoriesLoader.FailureHandler.handleMessage;

public class ChannelInBoundInterceptor implements ChannelInterceptor {

    private static final Logger log = LoggerFactory.getLogger(ChannelInBoundInterceptor.class);
    private final ChatService chatService;

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
}
