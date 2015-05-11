package pw.spn.quizgame.security;

import java.util.Collections;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public final class SecurityUtil {
    private SecurityUtil() {
    }

    public static Authentication createAuthentication(String playerID, String username, String password) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password,
                Collections.singleton(new DefaultAuthority()));
        token.setDetails(playerID);
        return token;
    }

    public static boolean isAuthenticated() {
        Authentication auth = getAuthentication();
        return auth instanceof UsernamePasswordAuthenticationToken && auth != null && auth.isAuthenticated();
    }

    public static String getCurrentPlayerID() {
        Authentication auth = getAuthentication();
        if (auth == null) {
            return null;
        }
        return (String) auth.getDetails();
    }

    public static String getCurrentPlayerName() {
        Authentication auth = getAuthentication();
        if (auth == null) {
            return null;
        }
        return (String) auth.getPrincipal();
    }

    private static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
