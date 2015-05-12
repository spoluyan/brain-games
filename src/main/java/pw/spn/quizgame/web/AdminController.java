package pw.spn.quizgame.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import pw.spn.quizgame.domain.Topic;
import pw.spn.quizgame.service.AdminService;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @RequestMapping("/")
    public String render() {
        return "admin";
    }

    @RequestMapping("/add")
    public String addQuestion(String topicId, String question, String answer1, String answer2, String answer3,
            String answer4, byte[] image) {
        adminService.addQuestion(topicId, question, answer1, answer2, answer3, answer4, image);
        return "redirect:/admin/";
    }

    @RequestMapping(value = "/topics", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Topic> getTopics() {
        return adminService.getTopics();
    }
}
