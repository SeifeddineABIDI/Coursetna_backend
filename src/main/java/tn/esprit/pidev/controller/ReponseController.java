package tn.esprit.pidev.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.pidev.entities.Reponse;
import tn.esprit.pidev.services.IGestionReponse;

import java.util.List;

@RestController
@RequestMapping("/Reponse")
public class ReponseController {
    @Autowired
    IGestionReponse iGestionReponse;

    //*************---Retrieve all reponses *******////
    @GetMapping("/GetAllReponses")
    public List<Reponse> retrieveAllReponses() {
        return iGestionReponse.retrieveAllReponses();
    }
    //*************---add response *******////

    @PostMapping("/addReponse/{id}")
    public Reponse addReponse(@RequestBody Reponse reponse) {
        return iGestionReponse.addReponse(reponse);
    }

    //*************---get response by id*******////
    @GetMapping("/getReponse/{id}")
    public Reponse RetrieveReponse(@PathVariable("id") long idrep) {
        return iGestionReponse.RetrieveReponse(idrep);
    }
    //***************** update response--- *****************

    @PutMapping("/updateReponse")
    public Reponse updateReponse(@RequestBody Reponse reponse) {
        return iGestionReponse.updateReponse(reponse);
    }
    //***************** delete response--- *****************

    @DeleteMapping("deleteReponse/{id}")
    public void deleteReponse(@PathVariable("id") long idrep) {
        iGestionReponse.deleteReponse(idrep);

    }
    //********** ---addReponseAndAssignToReclamationAndUser---**********//
    @PostMapping("/addReponseAndAssignToReclamationAndUser/{numReclamtion}/{numUser}")
    public Reponse addReponseAndAssignToReclamationAndUser(@RequestBody Reponse reponse,@PathVariable("numReclamtion") long numReclamtion,@PathVariable("numUser") long numUser)
    {
        return iGestionReponse.addReponseAndAssignToReclamationAndUser(reponse,numReclamtion,numUser);
    }

}