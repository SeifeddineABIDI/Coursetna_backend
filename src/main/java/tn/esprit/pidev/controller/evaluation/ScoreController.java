package tn.esprit.pidev.controller.evaluation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.pidev.entities.evaluation.Score;
import tn.esprit.pidev.services.evaluation.IGestionScore;

import java.util.List;

@RestController
@RequestMapping("/score")
@CrossOrigin
public class ScoreController {
    @Autowired
    IGestionScore IgScore;


    @GetMapping("/getAll")
    public List<Score> retrieveAllScores(){
        return IgScore.retrieveAllScores();
    }

    @GetMapping("/getScore/{id}")
    public Score retrieveScore (@PathVariable("id") Long numScore){
        return IgScore.retrieveScore(numScore);
    }

    @PostMapping("/addScore/{numQuiz}/{numUser}")
    public Score addScore(@PathVariable("numQuiz")Long numQuiz,@PathVariable("numUser")Long numUser){
        return IgScore.calculateScore(numQuiz,numUser);
    }


}
