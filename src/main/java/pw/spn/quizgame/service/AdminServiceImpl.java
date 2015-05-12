package pw.spn.quizgame.service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pw.spn.quizgame.domain.Question;
import pw.spn.quizgame.domain.RightAnswer;
import pw.spn.quizgame.domain.Topic;
import pw.spn.quizgame.repository.QuestionRepository;
import pw.spn.quizgame.repository.RightAnswerRepository;
import pw.spn.quizgame.repository.TopicRepository;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private RightAnswerRepository rightAnswerRepository;

    @Autowired
    private TopicRepository topicRepository;

    @Override
    public void addQuestion(String topicId, String question, String rightAnswer, String answer2, String answer3,
            String answer4, byte[] image) {
        List<String> answers = Arrays.asList(rightAnswer, answer2, answer3, answer4);
        Collections.shuffle(answers);
        int rightAnswerIndex = 0;
        for (int i = 0; i < 4; i++) {
            if (answers.get(i).equals(rightAnswer)) {
                rightAnswerIndex = i;
                break;
            }
        }
        Question q = new Question(topicId, question, image, answers.toArray(new String[4]));
        q = questionRepository.save(q);

        RightAnswer answer = new RightAnswer(q.getId(), rightAnswerIndex);
        rightAnswerRepository.save(answer);
    }

    @Override
    public List<Topic> getTopics() {
        return topicRepository.findAll();
    }

}
