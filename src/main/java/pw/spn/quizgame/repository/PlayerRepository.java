package pw.spn.quizgame.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import pw.spn.quizgame.domain.Player;

public interface PlayerRepository extends MongoRepository<Player, String> {
    Player findByLoginIgnoreCase(String login);
}
