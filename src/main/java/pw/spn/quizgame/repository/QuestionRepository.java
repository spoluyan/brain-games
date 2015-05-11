package pw.spn.quizgame.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import pw.spn.quizgame.domain.Question;

public interface QuestionRepository extends MongoRepository<Question, String> {
    List<Question> findByTopicId(String topicId);

    Long countByTopicId(String topicId);
}
