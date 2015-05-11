package pw.spn.quizgame.service;

import java.util.List;
import java.util.Map;

import pw.spn.quizgame.domain.GameState;
import pw.spn.quizgame.domain.Player;
import pw.spn.quizgame.exception.DuplicateUserException;

public interface PlayerService {
    Player register(String login, String password) throws DuplicateUserException;

    List<GameState> getPlayerGameStates();

    GameState getCurrentGameState();

    Map<String, String> getAvailablePlayers();

    void startGame(String competitorId, String competitorName);
}
