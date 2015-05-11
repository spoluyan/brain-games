package pw.spn.quizgame.domain;

import org.springframework.data.annotation.Id;

public class Statistics {
    @Id
    private String id;

    private String playerId;
    private int ratePoints;
    private int place;
    private int totalGames;
    private int wins;
    private int draws;
    private int looses;
    private int flawlessVictories;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public int getRatePoints() {
        return ratePoints;
    }

    public void setRatePoints(int ratePoints) {
        this.ratePoints = ratePoints;
    }

    public int getPlace() {
        return place;
    }

    public void setPlace(int place) {
        this.place = place;
    }

    public int getTotalGames() {
        return totalGames;
    }

    public void setTotalGames(int totalGames) {
        this.totalGames = totalGames;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getDraws() {
        return draws;
    }

    public void setDraws(int draws) {
        this.draws = draws;
    }

    public int getLooses() {
        return looses;
    }

    public void setLooses(int looses) {
        this.looses = looses;
    }

    public int getFlawlessVictories() {
        return flawlessVictories;
    }

    public void setFlawlessVictories(int flawlessVictories) {
        this.flawlessVictories = flawlessVictories;
    }
}
