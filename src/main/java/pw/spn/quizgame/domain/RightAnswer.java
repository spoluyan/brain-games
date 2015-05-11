package pw.spn.quizgame.domain;

import org.springframework.data.annotation.Id;

public class RightAnswer {
    @Id
    private String id;

    private String questionId;
    private int answerIndex;

    public RightAnswer() {
    }

    public RightAnswer(String questionId, int answerIndex) {
        this.questionId = questionId;
        this.answerIndex = answerIndex;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public int getAnswerIndex() {
        return answerIndex;
    }

    public void setAnswerIndex(int answerIndex) {
        this.answerIndex = answerIndex;
    }
}
