package pw.spn.quizgame.domain;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class GameState {
    @Id
    private String id;

    private String playerId;
    private String playerName;

    private boolean yourTurn;
    private boolean isPlayingNow;
    private String competitorId;
    private String competitorName;
    private int points;
    private int competitorPoints;
    private List<PlayedRound> playedRounds = new ArrayList<>(6);
    private GameResult gameResult = GameResult.NO_RESULT;

    @JsonIgnore
    public PlayedRound getLastPlayedRound() {
        int playedRoundsSize = playedRounds.size();
        if (playedRoundsSize == 0) {
            return null;
        }
        return playedRounds.get(playedRoundsSize - 1);
    }

    @JsonIgnore
    public PlayedRound getLastPlayedRound(String topicId) {
        PlayedRound round = getLastPlayedRound();
        if (round == null || !round.getTopic().getId().equals(topicId)) {
            return null;
        }
        return round;
    }

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

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public boolean isYourTurn() {
        return yourTurn;
    }

    public void setYourTurn(boolean yourTurn) {
        this.yourTurn = yourTurn;
    }

    public boolean isPlayingNow() {
        return isPlayingNow;
    }

    public void setPlayingNow(boolean isPlayingNow) {
        this.isPlayingNow = isPlayingNow;
    }

    public String getCompetitorId() {
        return competitorId;
    }

    public void setCompetitorId(String competitorId) {
        this.competitorId = competitorId;
    }

    public String getCompetitorName() {
        return competitorName;
    }

    public void setCompetitorName(String competitorName) {
        this.competitorName = competitorName;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getCompetitorPoints() {
        return competitorPoints;
    }

    public void setCompetitorPoints(int competitorPoints) {
        this.competitorPoints = competitorPoints;
    }

    public List<PlayedRound> getPlayedRounds() {
        return playedRounds;
    }

    public void setPlayedRounds(List<PlayedRound> playedRounds) {
        this.playedRounds = playedRounds;
    }

    public GameResult getGameResult() {
        return gameResult;
    }

    public void setGameResult(GameResult gameResult) {
        this.gameResult = gameResult;
    }
}
