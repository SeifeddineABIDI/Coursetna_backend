package tn.esprit.pidev.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.pidev.entities.Reclamtion;
import tn.esprit.pidev.services.IGestionReclamation;

import java.util.List;

@RestController
@RequestMapping("/Reclamation")
public class ReclamationController {
    @Autowired
    IGestionReclamation iGestionReclamation;

    //*************---Retrieve all reclamation *******////
    @GetMapping("/GetAllReclamtions")
    public List<Reclamtion> getall()
    {
        return  iGestionReclamation.retrieveAllReclamation();
    }

    //******************* ---addReclamation--- ************
    @PostMapping("/addReclamation")
    public Reclamtion addReclamation(@RequestBody Reclamtion reclamtion){
        return  iGestionReclamation.addReclamation(reclamtion);
    }
    //******************* --- getreclamation --- ************
    @GetMapping("/getreclamation/{id}")
    public Reclamtion RetrieveReclamation(@PathVariable("id") long idrec) {
        return iGestionReclamation.RetrieveReclamation(idrec);
    }

    //***************** update reclamation--- *****************
    @PutMapping("/updateReclamtion")
        public Reclamtion updateReclamtion(@RequestBody Reclamtion reclamtion)    {
        return iGestionReclamation.updateReclamtion(reclamtion);
    }

    //************* ----delete Reclamtion----*************//

    @DeleteMapping("/deleReclamtion/{id}")
    public void deleteReclamation(@PathVariable("id") long idrec) {
        iGestionReclamation.deleteReclamation(idrec);
    }

    @PostMapping("/addReclamtionAndAssignToUser/{id}")
    public Reclamtion addReclamtionAndAssignToUser(@RequestBody Reclamtion reclamtion,@PathVariable("id") Long id)
    {
        return iGestionReclamation.addReclamtionAndAssignToUser(reclamtion,id);
    }


    @GetMapping("/getReclamationByUserAndResponse/{userId}/{responseId}")
    public Reclamtion getReclamationByUserandReponse(@PathVariable("userId") Long userId, @PathVariable("responseId") Long responseId) {
        return iGestionReclamation.getReclamationByUserandReponse(userId, responseId);
    }

    @GetMapping("/reclamationsWithUserAndResponse")
    public List<Reclamtion> findAllReclamationsWithUserAndResponse() {
        return iGestionReclamation.findAllReclamationsWithUserAndResponse();
    }


}
