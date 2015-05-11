package pw.spn.quizgame.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import pw.spn.quizgame.domain.Topic;

public interface TopicRepository extends MongoRepository<Topic, String> {
    List<Topic> findByIdNotIn(Collection<String> id);
}
