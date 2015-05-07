package pw.spn.quizgame.web;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public abstract class BaseController {
    protected String redirectURL = "redirect:/game";

    protected boolean isAuthenticated() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth instanceof UsernamePasswordAuthenticationToken && auth != null && auth.isAuthenticated();
    }
}
