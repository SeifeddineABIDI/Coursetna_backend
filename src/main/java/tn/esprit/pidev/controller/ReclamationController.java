package tn.esprit.pidev.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.pidev.entities.Reclamtion;
import tn.esprit.pidev.repository.IReclamationRepository;
import tn.esprit.pidev.services.IGestionReclamation;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@CrossOrigin("*")
@RequestMapping("/Reclamation")
public class ReclamationController {
    @Autowired
    IGestionReclamation iGestionReclamation;

    @Autowired
    IReclamationRepository reclamationRepository ;

    //*************---Retrieve all reclamation *******////
    @GetMapping("/GetAllReclamtions")
    public List<Reclamtion> getall()
    {
        return  iGestionReclamation.retrieveAllReclamation();
    }


    @GetMapping("/GetAllReclamtionsbyUser/{userID}")
    public List<Reclamtion> getall(@PathVariable long userID) {
        return reclamationRepository.getTraitedReclamationsByUserId(userID);
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
    //************* ----addReclamtionAndAssignToUser---*************//
    @PostMapping("/addReclamtionAndAssignToUser/{id}")
    public Reclamtion addReclamtionAndAssignToUser(@RequestBody Reclamtion reclamtion,@PathVariable("id") Integer id) {
        return iGestionReclamation.addReclamtionAndAssignToUser(reclamtion, id);
    }
    //************* ----getReclamationByUserAndRespons---*************//
    @GetMapping("/getReclamationByUserAndResponse/{userId}/{responseId}")
    public Reclamtion getReclamationByUserandReponse(@PathVariable("userId") Integer userId, @PathVariable("responseId") Long responseId) {
        return iGestionReclamation.getReclamationByUserandReponse(userId, responseId);
    }

    @GetMapping("/reclamationsWithUserAndResponse")
    public List<Reclamtion> findAllReclamationsWithUserAndResponse() {
        return iGestionReclamation.findAllReclamationsWithUserAndResponse();
    }

        @PostMapping("/addReclamtionAndAssignToUserandTopic/{idUser}/{idTopic}")
    public String addReclamtionAndAssignToUserandTopic(@RequestBody Reclamtion reclamtion, @PathVariable("idUser") Integer idUser, @PathVariable("idTopic") Long idTopic) {
    return iGestionReclamation.addReclamationAndAssignToUserAndTopic(reclamtion,idUser,idTopic);
    }

    @GetMapping("/all")
    public List<Reclamtion> getAllReclamationsWithDetails() {
        // Appel du service pour obtenir toutes les réclamations avec les détails associés.
        return iGestionReclamation.getAllReclamationsWithDetails();
    }

    @GetMapping("/getReclamationsByUserAndTopic/{idUser}/{idTopic}")
    public List<Reclamtion> getReclamationsByUserAndTopic(@PathVariable("idUser") Integer idUser, @PathVariable("idTopic") Long idTopic) {
        // Appelez le service pour obtenir les réclamations selon l'ID d'utilisateur et de sujet.
        return iGestionReclamation.getAllDetails(new Reclamtion(), idUser, idTopic);
    }

    @PutMapping("/update/{userId}/{reclamationId}")
    public ResponseEntity<String> updateReclamationByUserAndReclamationId(
            @PathVariable Integer userId,
            @PathVariable Long reclamationId,
            @RequestBody Reclamtion updatedReclamation
    ) {
        try {
            String message = iGestionReclamation.updateReclamationByUserAndReclamationId(userId, reclamationId, updatedReclamation);
            return ResponseEntity.ok().body(message);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Reclamtion>> getReclamationsByUserId(@PathVariable Integer userId) {
        List<Reclamtion> reclamations = iGestionReclamation.getReclamationsByUserId(userId);
        return ResponseEntity.ok().body(reclamations);
    }

    @DeleteMapping("/delete/{userId}/{reclamationId}")
    public ResponseEntity<String> deleteReclamationByUserAndReclamationId(
            @PathVariable Integer userId,
            @PathVariable Long reclamationId
    ) {
        try {
            iGestionReclamation.deleteReclamationByUserAndReclamationId(userId, reclamationId);
            return ResponseEntity.ok().body("Reclamation deleted successfully.");
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    @DeleteMapping("/deleteReclamationById/{id}")
    public ResponseEntity<String> deleteReclamationById(@PathVariable("id") long id) {
        String message = iGestionReclamation.deleteReclamationbyID(id);

        // Check the message and return the appropriate response
        if (message.startsWith("Reclamation deleted successfully.")) {
            return ResponseEntity.ok().body(message);
        } else if (message.startsWith("Reclamation not found")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
        }
    }
   /* @GetMapping("/reclamationsWithResponses")
    public ResponseEntity<List<Reclamtion>> getAllReclamationsWithResponses() {
        List<Reclamtion> reclamationsWithResponses = iGestionReclamation.getAllReclamationsWithResponses();

        if (reclamationsWithResponses.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } else {
            return ResponseEntity.ok().body(reclamationsWithResponses);
        }
    }*/

    @GetMapping("/reclamationswithresponses")
    public List<Reclamtion> getAllReclamationsWithResponses() {
        return iGestionReclamation.findAllReclamationsWithResponses();
    }


    @GetMapping("/rechercher")
    public List<Reclamtion> rechercherReclamations(
            @RequestParam(required = false) String titre,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) String status) {
        return iGestionReclamation.rechercherReclamationsAvancee(titre, description, status);
    }

    @GetMapping("/stats")
    public String getStatistiques() {
        long nombreReclamationsTraitees = iGestionReclamation.countReclamationsTraitees();
        long nombreReclamationsNonTraitees = iGestionReclamation.countReclamationsNonTraitees();

        return "Nombre de réclamations traitées : " + nombreReclamationsTraitees +
                ", Nombre de réclamations non traitées : " + nombreReclamationsNonTraitees;
    }
}
