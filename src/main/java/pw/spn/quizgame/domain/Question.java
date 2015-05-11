package pw.spn.quizgame.domain;

import org.springframework.data.annotation.Id;

public class Question {
    @Id
    private String id;

    private String topicId;
    private String question;
    private byte[] image;
    private String[] answers;

    public Question() {
    }

    public Question(String topicId, String question, String[] answers) {
        this.topicId = topicId;
        this.question = question;
        this.answers = answers;
    }

    public Question(String topicId, String question, byte[] image, String[] answers) {
        this.topicId = topicId;
        this.question = question;
        this.image = image;
        this.answers = answers;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String[] getAnswers() {
        return answers;
    }

    public void setAnswers(String[] answers) {
        this.answers = answers;
    }
}
