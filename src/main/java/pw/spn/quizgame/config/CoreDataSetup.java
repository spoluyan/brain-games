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

        Topic t1 = topicRepository.save(new Topic("Искусство и культура"));
        Topic t2 = topicRepository.save(new Topic("Математика"));

        Question q1 = new Question();
        q1.setQuestion("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin quis sem vel turpis euismod tincidunt sed at odio. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Pellentesque vel ligula augue. Sed sodales eget metus sed pulvinar. Duis eu lectus sed neque tempus tincidunt sit amet a neque. Maecenas blandit dui tortor, sed facilisis enim pretium at. Cras sollicitudin ipsum in mauris faucibus, at efficitur lacus tempor. Donec lorem erat, tristique at urna non, mattis faucibus nunc. In nec ante vitae enim hendrerit laoreet. Nunc et sagittis felis. Suspendisse eget volutpat neque. ");
        q1.setAnswers(new String[] { "1", "2", "3", "4" });
        q1.setTopicId(t1.getId());

        q1 = questionRepository.save(q1);

        RightAnswer ra1 = new RightAnswer(q1.getId(), 0);
        rightAnswerRepository.save(ra1);

        Question q2 = new Question();
        q2.setQuestion("How?");
        q2.setAnswers(new String[] { "1", "3", "4", "5" });
        q2.setTopicId(t2.getId());

        q2 = questionRepository.save(q2);
        RightAnswer ra2 = new RightAnswer(q2.getId(), 1);
        rightAnswerRepository.save(ra2);

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

        /*
         * Player ninja = playerRepository.findByLoginIgnoreCase("ninja");
         * Player test1 = playerRepository.findByLoginIgnoreCase("test1");
         * Player test2 = playerRepository.findByLoginIgnoreCase("test2");
         * 
         * List<Topic> topics = topicRepository.findAll();
         * 
         * List<PlayedRound> playedRounds = new ArrayList<>(); PlayedRound
         * round1 = new PlayedRound(); round1.setTopic(topics.get(0));
         * round1.setAnswers(new boolean[] { true, true, false });
         * round1.setCompetitorAnswers(new boolean[] { false, true, false });
         * 
         * PlayedRound round2 = new PlayedRound();
         * round2.setTopic(topics.get(1)); round2.setCompetitorAnswers(new
         * boolean[] { false, true, true });
         * 
         * playedRounds.add(round1); playedRounds.add(round2);
         * 
         * GameState gameStateNinjaVsTest1 = new GameState();
         * gameStateNinjaVsTest1.setPlayerId(ninja.getId());
         * gameStateNinjaVsTest1.setPlayerName(ninja.getLogin());
         * gameStateNinjaVsTest1.setCompetitorId(test1.getId());
         * gameStateNinjaVsTest1.setCompetitorName(test1.getLogin());
         * gameStateNinjaVsTest1.setYourTurn(true);
         * gameStateNinjaVsTest1.setPoints(2);
         * gameStateNinjaVsTest1.setCompetitorPoints(3);
         * gameStateNinjaVsTest1.setPlayedRounds(playedRounds);
         * 
         * gameStateRepository.save(gameStateNinjaVsTest1);
         * 
         * GameState gameStateNinjaVsTest2 = new GameState();
         * gameStateNinjaVsTest2.setPlayerId(ninja.getId());
         * gameStateNinjaVsTest2.setPlayerName(ninja.getLogin());
         * gameStateNinjaVsTest2.setCompetitorId(test2.getId());
         * gameStateNinjaVsTest2.setCompetitorName(test2.getLogin());
         * gameStateNinjaVsTest2.setYourTurn(true);
         * 
         * gameStateRepository.save(gameStateNinjaVsTest2);
         * 
         * playedRounds = new ArrayList<>(); round1 = new PlayedRound();
         * round1.setTopic(topics.get(0)); round1.setCompetitorAnswers(new
         * boolean[] { true, true, false }); round1.setAnswers(new boolean[] {
         * false, true, false });
         * 
         * round2 = new PlayedRound(); round2.setTopic(topics.get(1));
         * round2.setAnswers(new boolean[] { false, true, true });
         * 
         * playedRounds.add(round1); playedRounds.add(round2);
         * 
         * GameState gameStateTest1VsNinja = new GameState();
         * gameStateTest1VsNinja.setPlayerId(test1.getId());
         * gameStateTest1VsNinja.setPlayerName(test1.getLogin());
         * gameStateTest1VsNinja.setCompetitorId(ninja.getId());
         * gameStateTest1VsNinja.setCompetitorName(ninja.getLogin());
         * gameStateTest1VsNinja.setYourTurn(false);
         * gameStateTest1VsNinja.setPoints(3);
         * gameStateTest1VsNinja.setCompetitorPoints(2);
         * gameStateTest1VsNinja.setPlayedRounds(playedRounds);
         * 
         * gameStateRepository.save(gameStateTest1VsNinja);
         * 
         * GameState gameStateTest2VsNinja = new GameState();
         * gameStateTest2VsNinja.setPlayerId(test2.getId());
         * gameStateTest2VsNinja.setPlayerName(test2.getLogin());
         * gameStateTest2VsNinja.setCompetitorId(ninja.getId());
         * gameStateTest2VsNinja.setCompetitorName(ninja.getLogin());
         * gameStateTest2VsNinja.setYourTurn(false);
         * 
         * gameStateRepository.save(gameStateTest2VsNinja);
         */
    }
}
