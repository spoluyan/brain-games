package pw.spn.quizgame.service;

import java.util.List;

import pw.spn.quizgame.domain.Question;
import pw.spn.quizgame.domain.Topic;

public interface GameService {
    List<Topic> getTopicsForTurn(String gameStateId);

    Question getQuestion(String gameStateId, String topicId);

    int answer(String gameStateId, int answer);
    
    void complete(String gameStateId);
}
