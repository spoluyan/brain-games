package pw.spn.quizgame.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import pw.spn.quizgame.security.SecurityUtil;

@Controller
public class LoginController extends BaseController {
    @RequestMapping("/")
    public String login() {
        if (SecurityUtil.isAuthenticated()) {
            return redirectURL;
        }
        return "index";
    }
}
