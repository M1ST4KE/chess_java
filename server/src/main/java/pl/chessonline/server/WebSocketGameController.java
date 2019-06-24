package pl.chessonline.server;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;


@Controller
public class WebSocketGameController {
    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/game")
    public WebSocketChessGame sendMessage(@Payload WebSocketChessGame webSocketChatMessage) {
        return webSocketChatMessage;
    }
    @MessageMapping("/chat.newUser")
    @SendTo("/topic/game")
    public WebSocketChessGame newUser(@Payload WebSocketChessGame webSocketChatMessage,
                                        SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("username", webSocketChatMessage.getSender());
        return webSocketChatMessage;
    }
}