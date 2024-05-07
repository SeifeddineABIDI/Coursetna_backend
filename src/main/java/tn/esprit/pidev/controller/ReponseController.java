package tn.esprit.pidev.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.pidev.entities.Reponse;
import tn.esprit.pidev.services.IGestionReponse;

import java.util.List;

@RestController
@CrossOrigin("*")

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

    @PostMapping("/addReponse")
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


    //***************** --- addReponseAndAssignToReclamtion --- ***************
    @PostMapping("/addReponseAndAssignToReclamtion/{idrep}")
    public Reponse addReponseAndAssignToReclamtion(@RequestBody Reponse reponse,@PathVariable("idrep") long idrep)
    {
        return iGestionReponse.addReponseAndAssignToReclamtion(reponse,idrep);
    }

    @PostMapping("/reclamations/{id}")
    public ResponseEntity<String> addResponseToReclamation(
            @RequestBody Reponse reponse,
            @PathVariable("id") long reclamationId
    ) {
        String message = iGestionReponse.AddReponseAndAssignToReclamtion(reponse, reclamationId);

        // Check the message and return the appropriate response
        if (message.startsWith("Response added successfully")) {
            return ResponseEntity.status(HttpStatus.CREATED).body(message);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
    }

}