package pw.spn.quizgame.security;

import org.springframework.security.core.GrantedAuthority;

public class DefaultAuthority implements GrantedAuthority {
    private static final long serialVersionUID = 94608211060097576L;

    private static final String DEFAULT_ROLE = "user";

    @Override
    public String getAuthority() {
        return DEFAULT_ROLE;
    }

}
