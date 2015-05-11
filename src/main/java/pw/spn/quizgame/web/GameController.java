package pw.spn.quizgame.web;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import pw.spn.quizgame.domain.GameState;
import pw.spn.quizgame.domain.Question;
import pw.spn.quizgame.domain.Topic;
import pw.spn.quizgame.service.GameService;
import pw.spn.quizgame.service.PlayerService;

@Controller
@RequestMapping("/game")
public class GameController extends BaseController {
    @Autowired
    private PlayerService playerService;

    @Autowired
    private GameService gameService;

    @RequestMapping("/")
    public String render() {
        return "game";
    }

    @RequestMapping(value = "/state", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<GameState> getGameStates() {
        return playerService.getPlayerGameStates();
    }
    
    @RequestMapping(value = "/state/current", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public GameState getCurrentGameState() {
        return playerService.getCurrentGameState();
    }

    @RequestMapping(value = "/competitors", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, String> getAvailablePlayers() {
        return playerService.getAvailablePlayers();
    }

    @RequestMapping("/start/{competitorId}/{competitorName}")
    @ResponseStatus(value = HttpStatus.OK)
    public void startGame(@PathVariable String competitorId, @PathVariable String competitorName) {
        playerService.startGame(competitorId, competitorName);
    }

    @RequestMapping(value = "/turn/topic/{gameStateId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Topic> chooseTopic(@PathVariable String gameStateId) {
        return gameService.getTopicsForTurn(gameStateId);
    }

    @RequestMapping(value = "/turn/topic/{gameStateId}/{topicId}/q", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Question getQuestion(@PathVariable String gameStateId, @PathVariable String topicId) {
        return gameService.getQuestion(gameStateId, topicId);
    }

    @RequestMapping(value = "/turn/{gameStateId}/{answer}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public int answer(@PathVariable String gameStateId, @PathVariable int answer) {
        return gameService.answer(gameStateId, answer);
    }
}
