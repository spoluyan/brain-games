package pw.spn.quizgame.config;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import pw.spn.quizgame.domain.Player;
import pw.spn.quizgame.repository.PlayerRepository;
import pw.spn.quizgame.util.CryptoUtil;

@Component
@Lazy(false)
public final class CoreDataSetup {
    @Autowired
    private PlayerRepository playerRepository;

    @PostConstruct
    public void init() {
        Player player = new Player("ninja", CryptoUtil.encryptWithMD5("ninja"));
        playerRepository.save(player);
    }
}
