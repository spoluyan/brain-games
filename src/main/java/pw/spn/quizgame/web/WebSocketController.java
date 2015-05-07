package pw.spn.quizgame.web;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {
    @MessageMapping("/ws")
    @SendTo("/game/test")
    public String test() throws Exception {
        return "Hello";
    }
}
