package pw.spn.quizgame.service;

import pw.spn.quizgame.domain.Player;
import pw.spn.quizgame.exception.DuplicateUserException;

public interface PlayerService {
    Player register(String login, String password) throws DuplicateUserException;
}
