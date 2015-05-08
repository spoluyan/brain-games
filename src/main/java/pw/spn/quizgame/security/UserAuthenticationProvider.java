package pw.spn.quizgame.security;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import pw.spn.quizgame.domain.Player;
import pw.spn.quizgame.repository.PlayerRepository;
import pw.spn.quizgame.util.CryptoUtil;

@Component
public class UserAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private PlayerRepository playerRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String login = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();
        Player player = playerRepository.findByLoginIgnoreCase(login);
        if (player != null) {
            String encryptedPassword = CryptoUtil.encryptWithMD5(password);
            if (player.getPassword().equals(encryptedPassword)) {
                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(login, password,
                        Collections.singleton(new DefaultAuthority()));
                return token;
            }
        }
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.equals(authentication);
    }
}
