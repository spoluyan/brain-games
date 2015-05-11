package pw.spn.quizgame.service;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pw.spn.quizgame.domain.GameState;
import pw.spn.quizgame.domain.PlayedRound;
import pw.spn.quizgame.domain.Question;
import pw.spn.quizgame.domain.Topic;
import pw.spn.quizgame.repository.GameStateRepository;
import pw.spn.quizgame.repository.QuestionRepository;
import pw.spn.quizgame.repository.RightAnswerRepository;
import pw.spn.quizgame.repository.TopicRepository;
import pw.spn.quizgame.util.RandomUtil;

@Service
public class GameServiceImpl implements GameService {
    @Autowired
    private GameStateRepository gameStateRepository;

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private RightAnswerRepository rightAnswerRepository;

    @Override
    public List<Topic> getTopicsForTurn(String gameStateId) {
        GameState gameState = getGameStateById(gameStateId);

        PlayedRound round = gameState.getLastPlayedRound();

        if (round == null || round.getAnswers() != null) {
            Set<String> playedTopicsIDs = new HashSet<>();
            gameState.getPlayedRounds().forEach(r -> {
                playedTopicsIDs.add(r.getTopic().getId());
            });
            List<Topic> allTopics = topicRepository.findByIdNotIn(playedTopicsIDs);
            return RandomUtil.getRandomItemsFromList(allTopics, 3);
        }

        return Collections.singletonList(round.getTopic());
    }

    @Override
    public Question getQuestion(String gameStateId, String topicId) {
        Question q;

        GameState gameState = getGameStateById(gameStateId);
        gameState.setPlayingNow(true);

        PlayedRound round = gameState.getLastPlayedRound(topicId);
        if (round == null) {
            round = new PlayedRound();
            round.setTopic(topicRepository.findOne(topicId));
            gameState.getPlayedRounds().add(round);
        }
        if (round.getQuestions().size() <= round.getQuestionsCounter()) {
            Set<String> playedQustionsIDs = new HashSet<>();
            round.getQuestions().forEach(question -> {
                playedQustionsIDs.add(question.getId());
            });
            List<Question> questions = questionRepository.findByTopicIdAndIdNotIn(topicId, playedQustionsIDs);
            q = RandomUtil.getRandomItemFromList(questions);
            round.getQuestions().add(q);
        } else {
            q = round.getCurrentQuestion();
        }

        gameStateRepository.save(gameState);
        return q;
    }

    @Override
    public int answer(String gameStateId, int answer) {
        GameState gameState = getGameStateById(gameStateId);
        PlayedRound round = gameState.getLastPlayedRound();
        int rightAnswer = rightAnswerRepository.findByQuestionId(round.getCurrentQuestion().getId()).getAnswerIndex();

        boolean[] answers = round.getAnswers();
        if (answers == null) {
            answers = new boolean[3];
        }
        answers[round.getQuestionsCounter()] = (answer == rightAnswer);
        round.incrementQuestionsCounter();
        round.setAnswers(answers);

        if (round.getQuestionsCounter() == 3) {
            boolean stillPlayersTurn = round.getCompetitorAnswers() != null;

            gameState.setPlayingNow(false);
            int pointsForRound = 0;
            for (int i = 0; i < 3; i++) {
                if (answers[i]) {
                    pointsForRound++;
                }
            }
            gameState.setPoints(gameState.getPoints() + pointsForRound);
            gameState.setYourTurn(stillPlayersTurn);

            // update competitor game state
            GameState competitorGameState = gameStateRepository.findByPlayerIdAndCompetitorId(
                    gameState.getCompetitorId(), gameState.getPlayerId());
            competitorGameState.setYourTurn(!stillPlayersTurn);
            PlayedRound competitorRound = competitorGameState.getLastPlayedRound(round.getTopic().getId());
            if (competitorRound == null) {
                competitorRound = new PlayedRound();
                competitorRound.setTopic(round.getTopic());
                competitorRound.setQuestions(round.getQuestions());
                competitorGameState.getPlayedRounds().add(competitorRound);
            }
            competitorRound.setCompetitorAnswers(answers);
            competitorGameState.setCompetitorPoints(gameState.getPoints());
            gameStateRepository.save(competitorGameState);
        }

        gameStateRepository.save(gameState);

        return rightAnswer;
    }

    private GameState getGameStateById(String gameStateId) {
        return gameStateRepository.findOne(gameStateId);
    }
}
