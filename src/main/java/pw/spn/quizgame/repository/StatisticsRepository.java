package pw.spn.quizgame.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import pw.spn.quizgame.domain.Statistics;

public interface StatisticsRepository extends MongoRepository<Statistics, String> {
    Statistics findByPlayerId(String playerId);
}
