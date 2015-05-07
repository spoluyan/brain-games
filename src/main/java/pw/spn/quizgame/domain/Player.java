package pw.spn.quizgame.domain;

import org.springframework.data.annotation.Id;

public class Player {
    @Id
    private String id;

    private String login;
    private String password;
    
    public Player() {
    }

    public Player(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
