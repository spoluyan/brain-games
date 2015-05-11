package pw.spn.quizgame.service;

import pw.spn.quizgame.domain.GameState;
import pw.spn.quizgame.domain.Statistics;

public interface StatisticsService {
    Statistics getStatistics();

    void updateStatistics(GameState playedGame);
}
