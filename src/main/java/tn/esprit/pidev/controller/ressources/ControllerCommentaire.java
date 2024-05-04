package tn.esprit.pidev.controller.ressources;


import tn.esprit.pidev.entities.ressources.Commentaire;
import tn.esprit.pidev.services.ressources.IGestionCom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/comm")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ControllerCommentaire {

    @Autowired
    IGestionCom commentaire;

    @PostMapping("/addCom")
    public ResponseEntity<?> ajouterCommentaire(@RequestBody Commentaire comm) {
        ResponseEntity<?> response = commentaire.addCommennt(comm);
        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
    }



    @GetMapping("/ressource/{ressourceId}")
    public ResponseEntity<List<Commentaire>> getCommentairesByRessourceId(@PathVariable Long ressourceId) {
        List<Commentaire> commentaires = commentaire.getCommentaireByRessourceId(ressourceId);
        return new ResponseEntity<>(commentaires, HttpStatus.OK);
    }

    @GetMapping("/ressources/{id}/commentaires/nombre")
    public Long getNombreCommentairesByRessourceId(@PathVariable Long id) {
        return commentaire.getNombreCommentairesByRessourceId(id);
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



    @PostMapping("/{commentaireId}/like")
    public ResponseEntity<Void> ajouterLike(@PathVariable Long commentaireId) {
        commentaire.ajouterLike(commentaireId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public Commentaire getCommentaire(@PathVariable Long id) {
        return commentaire.getCommentaire(id);
    }

    @PostMapping("/{commentaireId}/dislike")
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


