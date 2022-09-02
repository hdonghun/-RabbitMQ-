package com.example.websocketstomp2;


import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class MessageController {

    private final SimpMessageSendingOperations messageSendingOperations;

    /*
        /pub/hello              메시지 발행
        /topic/channelId        구독
     */

    @MessageMapping("/hello") //클라이언트에서, /pub/hello로 메세지를 발행한다.
    public void newUser(@Payload Message message, SimpMessageHeaderAccessor headerAccessor) {

        headerAccessor.getSessionAttributes().put("username", message.getSender());
        messageSendingOperations.convertAndSend("/topic/" + message.getChannelId(), message); //메시지에 정의된 채널 id에 메시지를 보낸다.
                                                                                                        // /sub/channel/ 채널아이디에 구독중인 클라이언트에게 메시지를 보낸다.
    }
}

//channel id가 다르면 메시지를 받지 않는다. 즉, eddy라는 채널 아이디에 구독중인 사용자만 메시지를 받게 된다.