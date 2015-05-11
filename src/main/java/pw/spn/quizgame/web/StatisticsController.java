package pw.spn.quizgame.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import pw.spn.quizgame.domain.Statistics;
import pw.spn.quizgame.service.StatisticsService;

@Controller
public class StatisticsController {
    @Autowired
    private StatisticsService statisticsService;

    @RequestMapping(value = "/stat", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Statistics getStatistics() {
        return statisticsService.getStatistics();
    }
}
