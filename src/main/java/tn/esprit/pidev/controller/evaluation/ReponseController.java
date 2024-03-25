package tn.esprit.pidev.controller.evaluation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.pidev.entities.evaluation.Reponse;
import tn.esprit.pidev.services.evaluation.IGestionReponse;
import tn.esprit.pidev.services.evaluation.IGestionScore;

import java.util.List;

@RestController
@RequestMapping("/response")
public class ReponseController {
    @Autowired
    IGestionReponse IgResponse;


    @GetMapping("/getAll")
    public List<Reponse> retrieveAllReponses(){
        return IgResponse.retrieveAllReponses();
    }

    @GetMapping("/getReponse/{id}")
    public Reponse retrieveReponse (@PathVariable("id") Long numReponse){
        return IgResponse.retrieveReponse(numReponse);
    }

    @PostMapping("/addReponse")
    public Reponse addReponse(@RequestBody Reponse reponse){
        return IgResponse.addReponse(reponse);
    }

    @PutMapping("/updateReponse")
    public Reponse updateReponse (@RequestBody Reponse reponse){
        return IgResponse.updateReponse(reponse);
    }

    @DeleteMapping("/removeReponse/{id}")
    public void removeReponse(@PathVariable("id") Long numReponse){
        IgResponse.removeReponse(numReponse);
    }

    @PostMapping("addReponseAndAssignToQuestionAndUser/{numUser}/{numQuestion}")
    public Reponse addReponseAndAssignToQuestionAndUser(@RequestBody Reponse reponse,@PathVariable("numQuestion") Long numQuestion,@PathVariable("numUser") Long numUser){
        return IgResponse.addReponseAndAssignToQuestionAndUser(reponse,numQuestion,numUser);
    }
}
