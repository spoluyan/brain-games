package pw.spn.quizgame.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import pw.spn.quizgame.domain.Player;

public interface PlayerRepository extends MongoRepository<Player, String> {
    Player findByLoginIgnoreCase(String login);

    List<Player> findByIdNotIn(Collection<String> id);
}
