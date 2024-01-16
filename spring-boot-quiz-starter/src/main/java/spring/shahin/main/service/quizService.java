package spring.shahin.main.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import spring.shahin.main.model.Question;
import spring.shahin.main.model.QuestionForm;
import spring.shahin.main.model.Result;
import spring.shahin.main.repository.QuestionRepo;
import spring.shahin.main.repository.ResultRepo;

@Service
public class quizService {

	@Autowired
	Question question;
	@Autowired
	QuestionRepo qr;
	@Autowired
	QuestionForm qf;
	@Autowired
	ResultRepo resultRepo;
	@Autowired
	Result result;

	public QuestionForm getQuestion() {
		List<Question> allQuestions = qr.findAll();
		List<Question> quList = new ArrayList<Question>();
		Random random = new Random();

		for (int i = 0; i < 5; i++) {
			int rand = random.nextInt(allQuestions.size());
			quList.add(allQuestions.get(rand));
			allQuestions.remove(rand);
		}
		qf.setQuestions(quList);
		return qf;

	}

	public int getResult(QuestionForm qForm) {
		int correct = 0;
		
		for(Question q: qForm.getQuestions())
			if(q.getAns() == q.getChose())
				correct++;
		
		return correct;
	}
	
	public void saveScore(Result result) {
		Result saveResult = new Result();
		saveResult.setUsername(result.getUsername());
		saveResult.setTotalCorrect(result.getTotalCorrect());
		resultRepo.save(saveResult);
	}	
	public List<Result> getTopScore() {
		// TODO Auto-generated method stub
		List<Result> sList = resultRepo.findAll(Sort.by(Sort.Direction.DESC ,"totalCorrect"));
		return sList;
	}
}
