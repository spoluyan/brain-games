package pw.spn.quizgame.security;

import org.springframework.security.core.GrantedAuthority;

public class Authority implements GrantedAuthority {
    private static final long serialVersionUID = 94608211060097576L;

    private String role;

    public Authority(String role) {
        this.role = role;
    }

    @Override
    public String getAuthority() {
        return role;
    }

}
