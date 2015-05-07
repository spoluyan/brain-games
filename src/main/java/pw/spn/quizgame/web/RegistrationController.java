package pw.spn.quizgame.web;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import pw.spn.quizgame.exception.DuplicateUserException;
import pw.spn.quizgame.security.Authority;
import pw.spn.quizgame.service.PlayerService;

@Controller
public class RegistrationController extends BaseController {
    @Autowired
    private PlayerService playerService;

    @RequestMapping("/register")
    public String register() {
        if (isAuthenticated()) {
            return redirectURL;
        }
        return "registration";
    }

    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public String addUser(@RequestParam String username, @RequestParam String password) {
        try {
            playerService.register(username, password);
        } catch (DuplicateUserException e) {
            return "redirect:register?error";
        }
        authenticate(username, password);
        return "redirect:game";
    }

    private void authenticate(String username, String password) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password,
                Collections.singleton(new Authority("user")));
        SecurityContextHolder.getContext().setAuthentication(token);
    }
}
