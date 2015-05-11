package pw.spn.quizgame.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import pw.spn.quizgame.domain.GameState;

public interface GameStateRepository extends MongoRepository<GameState, String> {
    List<GameState> findByPlayerId(String playerId);

    GameState findByPlayerIdAndCompetitorId(String playerId, String competitorId);

    GameState findByPlayerIdAndIsPlayingNow(String playerId, boolean isPlayingNow);
}
