package com.jinjin.chat;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic");    // 메세지 브로커 활성화하고 subscribe 메세지 접두사 설정
        config.setApplicationDestinationPrefixes("/app");
        // 클라이언트에서 발송한 메시지중 Destination이 해당 경로(/app)로 시작하는 메시지를 메시지 브로커에서 처리하게합니다
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/chat")       // 웹소켓 엔드포인트 지정. 추후에 해당 경로로 클라이언트는 서버와 handshake 하게 됨.
                .setAllowedOriginPatterns("*")  // CORS 설정
                .withSockJS();
                // SockJS이 브라우저에서 웹소켓을 지원하지 않을 경우 대체 옵션을 지원
    }
}
