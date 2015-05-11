package pw.spn.quizgame.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import pw.spn.quizgame.domain.RightAnswer;

public interface RightAnswerRepository extends MongoRepository<RightAnswer, String> {
    RightAnswer findByQuestionId(String questionId);
}
