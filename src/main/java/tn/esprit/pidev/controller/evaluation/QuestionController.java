package tn.esprit.pidev.controller.evaluation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.pidev.entities.evaluation.Question;
import tn.esprit.pidev.services.evaluation.IGestionQuestion;

import java.util.List;

@RestController
@RequestMapping("/question")
@CrossOrigin
public class QuestionController {
    @Autowired
    IGestionQuestion IgQuestion;

    @GetMapping("/getAll")
    public List<Question> getAllQuestions() {
        return IgQuestion.retrieveAllQuestions();
    }

    @GetMapping("/getQuestion/{id}")
    public Question getQuestionById(@PathVariable("id") Long numQuestion) {
        return IgQuestion.retrieveQuestion(numQuestion);
    }

    @PutMapping("/updateQuestion/{id}")
    public Question updateQuestion(@RequestBody Question question,@PathVariable("id") Long numQuiz) {
        return IgQuestion.updateQuestion(question,numQuiz);
    }

    @DeleteMapping("/removeQuestion/{id}")
    public void removeQuestion(@PathVariable("id") Long numQuestion) {
        IgQuestion.removeQuestion(numQuestion);
    }

    @PostMapping("addQuestionAndAssignToQuiz/{idQuiz}")
    public Question addQuestionAndAssignToQuiz(@RequestBody Question question,@PathVariable("idQuiz") Long numQuiz){
        return IgQuestion.addQuestionAndAssignToQuiz(question,numQuiz);
    }

    @GetMapping("getAllQuestions/{id}")
    public List<Question> getQuestionsByQuiz(@PathVariable("id") Long numQuiz){
        return IgQuestion.getQuestionsByQuiz(numQuiz);
    }

}
