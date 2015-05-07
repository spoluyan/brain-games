package pw.spn.quizgame.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController extends BaseController {
    @RequestMapping("/")
    public String login() {
        if (isAuthenticated()) {
            return redirectURL;
        }
        return "index";
    }
}
