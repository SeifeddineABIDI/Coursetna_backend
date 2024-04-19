package tn.esprit.pidev.controller.ressources;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.pidev.entities.ressources.Commentaire;
import tn.esprit.pidev.services.ressources.IGestionCom;

import java.util.List;


@RestController
@RequestMapping("/comm")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ControllerCommentaire {

    @Autowired
    IGestionCom commentaire;

    @PostMapping("/addCom/{ressourceId}/{userId}")
    public ResponseEntity<Commentaire> ajouterCommentaire(@RequestBody Commentaire comm,
                                                          @PathVariable Integer userId,
                                                          @PathVariable Long ressourceId) {
        Commentaire nouveauCommentaire = commentaire.addComment(comm, userId, ressourceId);
        return new ResponseEntity<>(nouveauCommentaire, HttpStatus.CREATED);
    }
    @GetMapping("/ressource/{ressourceId}")
    public ResponseEntity<List<Commentaire>> getCommentairesByRessourceId(@PathVariable Long ressourceId) {
        List<Commentaire> commentaires = commentaire.getCommentaireByRessourceId(ressourceId);
        return new ResponseEntity<>(commentaires, HttpStatus.OK);
    }
    @GetMapping("/{commentaireId}/likes")
    public ResponseEntity<Integer> getLikes(@PathVariable Long commentaireId) {
        int totalLikes = commentaire.calculerTotalLikes(commentaireId);
        return ResponseEntity.ok(totalLikes);
    }

    @GetMapping("/{commentaireId}/dislikes")
    public ResponseEntity<Integer> getDislikes(@PathVariable Long commentaireId) {
        int totalDislikes = commentaire.calculerTotalDislikes(commentaireId);
        return ResponseEntity.ok(totalDislikes);
    }



    @PostMapping("/{commentaireId}/likes")
    public ResponseEntity<Void> ajouterLike(@PathVariable Long commentaireId) {
        commentaire.ajouterLike(commentaireId);
        return ResponseEntity.ok().build();
    }


    @PostMapping("/{commentaireId}/dislikes")
    public ResponseEntity<Void> ajouterDislike(@PathVariable Long commentaireId) {
        commentaire.ajouterDislike(commentaireId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{commentaireId}/likes")
    public ResponseEntity<Void> supprimerLike(@PathVariable Long commentaireId) {
        commentaire.supprimerLike(commentaireId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{commentaireId}/dislikes")
    public ResponseEntity<Void> supprimerDislike(@PathVariable Long commentaireId) {
        commentaire.supprimerDislike(commentaireId);
        return ResponseEntity.ok().build();
    }

}


