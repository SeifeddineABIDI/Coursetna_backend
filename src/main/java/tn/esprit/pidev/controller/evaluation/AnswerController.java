package tn.esprit.pidev.controller.user.evaluation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.pidev.entities.evaluation.Answer;
import tn.esprit.pidev.services.evaluation.IGestionAnswer;

import java.util.List;

@RestController
@RequestMapping("/answer")
@CrossOrigin
public class AnswerController {
    @Autowired
    IGestionAnswer IgResponse;


    @GetMapping("/getAll")
    public List<Answer> retrieveAllReponses(){
        return IgResponse.retrieveAllReponses();
    }

    @GetMapping("/getReponse/{id}")
    public Answer retrieveReponse (@PathVariable("id") Long numReponse){
        return IgResponse.retrieveReponse(numReponse);
    }

    @PostMapping("/addReponse")
    public Answer addReponse(@RequestBody Answer reponse){
        return IgResponse.addReponse(reponse);
    }

    @PutMapping("/updateReponse")
    public Answer updateReponse (@RequestBody Answer reponse){
        return IgResponse.updateReponse(reponse);
    }

    @DeleteMapping("/removeReponse/{id}")
    public void removeReponse(@PathVariable("id") Long numReponse){
        IgResponse.removeReponse(numReponse);
    }

    @PostMapping("addReponseAndAssignToQuestionAndUser/{numUser}/{numQuestion}")
    public Answer addReponseAndAssignToQuestionAndUser(@RequestBody Answer reponse,@PathVariable("numQuestion") Long numQuestion,@PathVariable("numUser") Integer numUser){
        return IgResponse.addReponseAndAssignToQuestionAndUser(reponse,numQuestion,numUser);
    }


    @GetMapping("/getAllByUser/{id}")
    public List<Answer> getAllResponsesByUser (@PathVariable("id") Integer numUser){
        return IgResponse.getAllByUser(numUser);
    }

}
