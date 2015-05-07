package pw.spn.quizgame.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pw.spn.quizgame.domain.Player;
import pw.spn.quizgame.exception.DuplicateUserException;
import pw.spn.quizgame.repository.PlayerRepository;
import pw.spn.quizgame.util.CryptoUtil;

@Service
public class PlayerServiceImpl implements PlayerService {
    @Autowired
    private PlayerRepository playerRepository;

    @Override
    public Player register(String login, String password) throws DuplicateUserException {
        Player player = playerRepository.findByLoginIgnoreCase(login);
        if (player != null) {
            throw new DuplicateUserException();
        }
        String encryptedPassword = CryptoUtil.encryptWithMD5(password);
        player = new Player(login, encryptedPassword);
        player = playerRepository.save(player);
        return player;
    }

}
