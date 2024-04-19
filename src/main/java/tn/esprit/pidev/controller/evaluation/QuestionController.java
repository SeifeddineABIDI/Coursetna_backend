package tn.esprit.pidev.controller.user.evaluation;

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

    @PostMapping("/addQuestion")
    public Question addQuestion(@RequestBody Question question) {
        return IgQuestion.addQuestion(question);
    }

    @PutMapping("/updateQuestion")
    public Question updateQuestion(@RequestBody Question question) {
        return IgQuestion.updateQuestion(question);
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
