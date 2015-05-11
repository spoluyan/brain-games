package pw.spn.quizgame.service;

import java.util.List;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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
        int placesDiff = statistics.getPlace() - competitorStatistics.getPlace();
        int halfDiff = placesDiff / 2;

        switch (playedGame.getGameResult()) {
        case DRAW:
            statistics.setDraws(statistics.getDraws() + 1);
            if (placesDiff > 0) {
                statistics.setRatePoints(statistics.getRatePoints() + halfDiff);
            }
            break;
        case LOOSE:
            statistics.setLooses(statistics.getLooses() + 1);
            if (placesDiff > 0) {
                statistics.setRatePoints(statistics.getRatePoints() - halfDiff - 1);
            } else {
                statistics.setRatePoints(statistics.getRatePoints() - placesDiff - 1);
            }
            break;
        case WIN:
            statistics.setWins(statistics.getWins() + 1);
            if (placesDiff > 0) {
                statistics.setRatePoints(statistics.getRatePoints() + placesDiff + 1);
            } else {
                statistics.setRatePoints(statistics.getRatePoints() + halfDiff + 1);
            }
            break;
        default:
            break;
        }

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
}
