package spring.shahin.main.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import spring.shahin.main.model.QuestionForm;
import spring.shahin.main.model.Result;
import spring.shahin.main.service.quizService;

@org.springframework.stereotype.Controller
public class Controller {
	@Autowired
	Result result;
	@Autowired
	quizService qService;
	Boolean submitted = false;

	@ModelAttribute("result")
	public Result getResult() {
		return result;
	}

	@GetMapping("/")
	private String home() {
		// TODO Auto-generated method stub
		return "index.html";
	}

	@PostMapping("/quiz")
	public String quiz(@RequestParam String username, Model m, RedirectAttributes rd) {
		if (username == null || username.equals("")) {
			rd.addFlashAttribute("warningMsg", "Please Enter Your Name.");
			return "redirect:/";
		}
		submitted = false;
		result.setUsername(username);
		QuestionForm qform = qService.getQuestion();
		m.addAttribute("qForm", qform);
		return "quiz.html";
	}

	
	@PostMapping("/submit")
	public String submit(@ModelAttribute QuestionForm qForm, Model m) {
		if(!submitted) {
			result.setTotalCorrect(qService.getResult(qForm));
			qService.saveScore(result);
			submitted = true;
		}
		return "result.html";
	}

	
	@GetMapping("/score")
	public String score(Model m) {
		List<Result> sList = qService.getTopScore();
		m.addAttribute("sList", sList);
		return "scoreBoard.html";
	}

}
