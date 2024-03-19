package tn.esprit.pidev.controller.evaluation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.pidev.entities.evaluation.Question;
import tn.esprit.pidev.services.evaluation.IGestionQuestion;

import java.util.List;

@RestController
@RequestMapping("/question")
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

    @GetMapping("/addQuestion")
    public Question addQuestion(@RequestBody Question question) {
        return IgQuestion.addQuestion(question);
    }

    @GetMapping("/updateQuestion")
    public Question updateQuestion(@RequestBody Question question) {
        return IgQuestion.updateQuestion(question);
    }

    @GetMapping("/removeQuestion/{id}")
    public void removeQuestion(@PathVariable("id") Long numQuestion) {
        IgQuestion.removeQuestion(numQuestion);
    }
}
