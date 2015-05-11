package pw.spn.quizgame.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import pw.spn.quizgame.domain.Player;
import pw.spn.quizgame.exception.DuplicateUserException;
import pw.spn.quizgame.security.SecurityUtil;
import pw.spn.quizgame.service.PlayerService;

@Controller
public class RegistrationController extends BaseController {
    @Autowired
    private PlayerService playerService;

    @RequestMapping("/register")
    public String register() {
        if (SecurityUtil.isAuthenticated()) {
            return redirectURL;
        }
        return "registration";
    }

    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public String addUser(@RequestParam String username, @RequestParam String password) {
        Player player;
        try {
            player = playerService.register(username, password);
        } catch (DuplicateUserException e) {
            return "redirect:register?error";
        }
        authenticate(player.getId(), username, password);
        return "redirect:game";
    }

    private void authenticate(String playerID, String username, String password) {
        Authentication auth = SecurityUtil.createAuthentication(playerID, username, password);
        SecurityContextHolder.getContext().setAuthentication(auth);
    }
}
