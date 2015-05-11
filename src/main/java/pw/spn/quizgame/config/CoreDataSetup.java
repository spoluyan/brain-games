package pw.spn.quizgame.config;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import pw.spn.quizgame.domain.Player;
import pw.spn.quizgame.domain.Question;
import pw.spn.quizgame.domain.RightAnswer;
import pw.spn.quizgame.domain.Topic;
import pw.spn.quizgame.repository.GameStateRepository;
import pw.spn.quizgame.repository.PlayerRepository;
import pw.spn.quizgame.repository.QuestionRepository;
import pw.spn.quizgame.repository.RightAnswerRepository;
import pw.spn.quizgame.repository.TopicRepository;
import pw.spn.quizgame.util.CryptoUtil;

@Component
@Lazy(false)
public final class CoreDataSetup {
    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private GameStateRepository gameStateRepository;

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private RightAnswerRepository rightAnswerRepository;

    @PostConstruct
    public void init() {
        createPlayers();

        createTopicsAndQuestions();

        createGameStates();
    }

    private void createTopicsAndQuestions() {
        topicRepository.deleteAll();
        questionRepository.deleteAll();
        rightAnswerRepository.deleteAll();

        for (int i = 0; i < 6; i++) {
            Topic t = topicRepository.save(new Topic("topic" + i));

            for (int j = 0; j < 3; j++) {
                Question q = new Question(t.getId(), "q" + j, new String[] { "1", "2", "3", "4" });
                q = questionRepository.save(q);
                RightAnswer ra = new RightAnswer(q.getId(), 0);
                rightAnswerRepository.save(ra);
            }
        }
    }

    private void createPlayers() {
        playerRepository.deleteAll();

        playerRepository.save(new Player("ninja", CryptoUtil.encryptWithMD5("ninja")));
        playerRepository.save(new Player("test1", CryptoUtil.encryptWithMD5("test1")));
        playerRepository.save(new Player("test2", CryptoUtil.encryptWithMD5("test2")));
        playerRepository.save(new Player("test3", CryptoUtil.encryptWithMD5("test3")));
    }

    private void createGameStates() {
        gameStateRepository.deleteAll();
    }
}
