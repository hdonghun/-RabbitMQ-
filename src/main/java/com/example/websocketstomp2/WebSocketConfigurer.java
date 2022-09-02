package com.example.websocketstomp2;


import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker //STOMP를 사용하기 위한 어노테이션           //@EnableWebSocket : 기본적인 웹소켓 사용을 위한 어노테이션
public class WebSocketConfigurer implements WebSocketMessageBrokerConfigurer {

    @Override   //TODO:property 분리
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/pub") //서버에서 클라이언트로부터의 메서지를 받을 api의 prefix설정, 아래topic러는 곳에 구독하면, 실제경로는 "topic/pub"
                .enableStompBrokerRelay("/topic") //SimpleBroker의 기능과 외부 message broker(RabbitMQ, ActiveMQ 등)에 메시지를 전달하는 기능을 가지고 있다.
                .setRelayHost("localhost")                 // 여기부터 외부브로커인 RabbitMQ를 사용하기위한 설정.
                .setVirtualHost("/")
                .setRelayPort(61613)
                .setClientLogin("guest")
                .setClientPasscode("guest");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")     //엔드포인트는 /ws
                .setAllowedOrigins("*");
    }
}