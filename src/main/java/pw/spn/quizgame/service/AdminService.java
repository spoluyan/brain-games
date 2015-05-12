package pw.spn.quizgame.service;

import java.util.List;

import pw.spn.quizgame.domain.Topic;

public interface AdminService {
    void addQuestion(String topicId, String question, String rightAnswer, String answer2, String answer3,
            String answer4, byte[] image);

    List<Topic> getTopics();
}
