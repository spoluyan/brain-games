package pw.spn.quizgame.domain;

import org.springframework.data.annotation.Id;

public class Topic {
    @Id
    private String id;

    private String name;

    public Topic() {
    }

    public Topic(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Topic)) {
            return false;
        }
        Topic topic = (Topic) obj;
        return this.name.equals(topic.name);
    }
}
