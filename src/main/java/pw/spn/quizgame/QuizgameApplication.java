package pw.spn.quizgame;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class QuizgameApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(QuizgameApplication.class);
        app.setShowBanner(false);
        app.run(args);
    }
}
