package pw.spn.quizgame.service;

import java.util.List;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import pw.spn.quizgame.domain.GameResult;
import pw.spn.quizgame.domain.GameState;
import pw.spn.quizgame.domain.Statistics;
import pw.spn.quizgame.repository.StatisticsRepository;
import pw.spn.quizgame.security.SecurityUtil;

@Service
public class StatisticsServiceImpl implements StatisticsService {
    @Autowired
    private StatisticsRepository statisticsRepository;

    @Override
    public Statistics getStatistics() {
        return getStatistics(SecurityUtil.getCurrentPlayerID());
    }

    @Override
    public void updateStatistics(GameState playedGame) {
        Statistics statistics = getStatistics(playedGame.getPlayerId());
        Statistics competitorStatistics = getStatistics(playedGame.getCompetitorId());

        int ratePoints = calculateRatePoints(statistics.getRatePoints(), competitorStatistics.getRatePoints(),
                playedGame.getGameResult());
        statistics.setRatePoints(statistics.getRatePoints() + ratePoints);

        if (playedGame.getPoints() == 18) {
            statistics.setFlawlessVictories(statistics.getFlawlessVictories() + 1);
        }
        statistics.setTotalGames(statistics.getTotalGames() + 1);
        statisticsRepository.save(statistics);

        recalculatePlaces();
    }

    private void recalculatePlaces() {
        List<Statistics> stats = statisticsRepository.findAll(new Sort(Sort.Direction.DESC, "ratePoints"));
        IntStream.range(0, stats.size()).forEach(i -> {
            stats.get(i).setPlace(i);
            statisticsRepository.save(stats.get(i));
        });
    }

    private Statistics getStatistics(String playerId) {
        return statisticsRepository.findByPlayerId(playerId);
    }

    private int calculateRatePoints(int firstPlayerRate, int secondPlayerRate, GameResult gameResult) {
        // based on the Elo rating system
        double e = 1 / (1 + Math.pow(10, (secondPlayerRate - firstPlayerRate) / 400));
        int k = 25;
        if (gameResult == GameResult.LOOSE) {
            k = 9;
        }
        double s;
        switch (gameResult) {
        case WIN:
            s = 1;
            break;
        case LOOSE:
            s = 0;
            break;
        default:
            s = 0.5;
            break;
        }
        int result = (int) (k * (s - e));
        if (gameResult == GameResult.DRAW && result < 0) {
            result = 0;
        }
        return result;
    }
}
