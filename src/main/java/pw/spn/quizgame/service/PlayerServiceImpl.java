package pw.spn.quizgame.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import pw.spn.quizgame.domain.GameState;
import pw.spn.quizgame.domain.Player;
import pw.spn.quizgame.domain.Statistics;
import pw.spn.quizgame.exception.DuplicateUserException;
import pw.spn.quizgame.repository.GameStateRepository;
import pw.spn.quizgame.repository.PlayerRepository;
import pw.spn.quizgame.repository.StatisticsRepository;
import pw.spn.quizgame.security.SecurityUtil;
import pw.spn.quizgame.util.CryptoUtil;

@Service
public class PlayerServiceImpl implements PlayerService {
    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private GameStateRepository gameStateRepository;

    @Autowired
    private StatisticsRepository statisticsRepository;

    @Override
    public Player register(String login, String password) throws DuplicateUserException {
        Player player = playerRepository.findByLoginIgnoreCase(login);
        if (player != null) {
            throw new DuplicateUserException();
        }
        String encryptedPassword = CryptoUtil.encryptWithMD5(password);
        player = new Player(login, encryptedPassword);
        player = playerRepository.save(player);

        Statistics stat = new Statistics();
        stat.setPlayerId(player.getId());
        stat.setPlace((int) (statisticsRepository.count() + 1));
        statisticsRepository.save(stat);
        return player;
    }

    @Override
    public List<GameState> getPlayerGameStates() {
        List<GameState> states = gameStateRepository.findByPlayerIdAndCompletedFalse(getCurrentPlayerId());
        if (states.size() == 0) {
            GameState state = new GameState();
            state.setPlayerId(getCurrentPlayerId());
            state.setPlayerName((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
            return Collections.singletonList(state);
        }
        return states;
    }

    @Override
    public GameState getCurrentGameState() {
        return gameStateRepository.findByPlayerIdAndIsPlayingNowTrue(getCurrentPlayerId());
    }

    @Override
    public Map<String, String> getAvailablePlayers() {
        List<GameState> states = gameStateRepository.findByPlayerIdAndCompletedFalse(getCurrentPlayerId());
        Set<String> exclusions = new HashSet<>(states.size() + 1);
        exclusions.add(getCurrentPlayerId());
        states.forEach(state -> {
            exclusions.add(state.getCompetitorId());
        });
        List<Player> availablePlayers = playerRepository.findByIdNotIn(exclusions);
        Map<String, String> result = new HashMap<>(availablePlayers.size());
        availablePlayers.forEach(player -> {
            result.put(player.getId(), player.getLogin());
        });
        return result;
    }

    @Override
    public void startGame(String competitorId, String competitorName) {
        // check if competitor has already started the game
        GameState gameState = gameStateRepository.findByPlayerIdAndCompetitorIdAndCompletedFalse(getCurrentPlayerId(), competitorId);
        if (gameState != null) {
            return;
        }
        String currentPlayerId = getCurrentPlayerId();
        String currentPlayerName = SecurityUtil.getCurrentPlayerName();
        gameState = new GameState();
        gameState.setPlayerId(currentPlayerId);
        gameState.setPlayerName(currentPlayerName);
        gameState.setCompetitorId(competitorId);
        gameState.setCompetitorName(competitorName);
        gameState.setYourTurn(true);
        gameStateRepository.save(gameState);

        GameState competitorGameState = new GameState();
        competitorGameState.setPlayerId(competitorId);
        competitorGameState.setPlayerName(competitorName);
        competitorGameState.setCompetitorId(currentPlayerId);
        competitorGameState.setCompetitorName(currentPlayerName);
        gameStateRepository.save(competitorGameState);
    }

    private String getCurrentPlayerId() {
        return SecurityUtil.getCurrentPlayerID();
    }
}
