package pw.spn.quizgame.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/game")
public class GameController {
    @RequestMapping("/")
    public String render() {
        return "game";
    }
}
