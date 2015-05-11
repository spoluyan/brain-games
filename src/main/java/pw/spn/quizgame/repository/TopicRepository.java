package pw.spn.quizgame.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import pw.spn.quizgame.domain.Topic;

public interface TopicRepository extends MongoRepository<Topic, String> {

}
