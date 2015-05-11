package pw.spn.quizgame.domain;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class PlayedRound {
    private Topic topic;
    private boolean[] answers;
    private boolean[] competitorAnswers;
    private int questionsCounter;
    private List<Question> questions = new ArrayList<>(3);

    public void incrementQuestionsCounter() {
        questionsCounter++;
    }

    @JsonIgnore
    public Question getCurrentQuestion() {
        return questions.get(questionsCounter);
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public boolean[] getAnswers() {
        return answers;
    }

    public void setAnswers(boolean[] answers) {
        this.answers = answers;
    }

    public boolean[] getCompetitorAnswers() {
        return competitorAnswers;
    }

    public void setCompetitorAnswers(boolean[] competitorAnswers) {
        this.competitorAnswers = competitorAnswers;
    }

    public int getQuestionsCounter() {
        return questionsCounter;
    }

    public void setQuestionsCounter(int questionsCounter) {
        this.questionsCounter = questionsCounter;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}
