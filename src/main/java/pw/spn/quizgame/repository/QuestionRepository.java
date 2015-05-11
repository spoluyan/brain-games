package pw.spn.quizgame.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import pw.spn.quizgame.domain.Question;

public interface QuestionRepository extends MongoRepository<Question, String> {
    List<Question> findByTopicIdAndIdNotIn(String topicId, Collection<String> id);
}
