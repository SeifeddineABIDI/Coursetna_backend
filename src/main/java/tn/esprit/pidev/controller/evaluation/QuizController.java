package tn.esprit.pidev.controller.evaluation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.pidev.entities.evaluation.Quiz;
import tn.esprit.pidev.services.evaluation.IGestionQuiz;

import java.util.List;

@RestController
@RequestMapping("/quiz")
@CrossOrigin
public class QuizController {
    @Autowired
    IGestionQuiz IgQuiz;

    @GetMapping("/getAll")
    public List<Quiz> retrieveAllQuizs(){
        return IgQuiz.retrieveAllQuizs();
    }
    @GetMapping("/getQuiz/{id}")
    Quiz retrieveQuiz (@PathVariable("id") Long numQuiz){
        return IgQuiz.retrieveQuiz(numQuiz);
    }

    @PutMapping("/updateQuiz")
    Quiz updateQuiz (@RequestBody Quiz quiz){
        return IgQuiz.updateQuiz(quiz);
    }

    @DeleteMapping("/removeQuiz/{id}")
    void removeQuiz(@PathVariable("id") Long numQuiz){
        IgQuiz.removeQuiz(numQuiz);
    }

    @PostMapping("/addQuizAndAssignToTopic/{numTopic}")
    public Quiz addQuizAndAssignToTopic(@RequestBody Quiz quiz,@PathVariable("numTopic") Long numTopic) { return IgQuiz.addQuizAndAssignToTopic(quiz, numTopic); }
    /******************************/
    @GetMapping("/getQuizNotEmpty")
    public List<Quiz> getQuizNotEmpty(){
        return IgQuiz.getQuizNotEmpty();
    }

}
