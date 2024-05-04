package tn.esprit.pidev.services.ressources;

import tn.esprit.pidev.entities.ressources.Commentaire;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IGestionCom {







    @Transactional
    ResponseEntity<?> addCommennt(Commentaire comm);


    List<Commentaire> getCommentaireByRessourceId(Long ressourceId);

    int calculerTotalLikes(Long commentaireId);

    int calculerTotalDislikes(Long commentaireId);

    void ajouterLike(Long commentaireId);

    void ajouterDislike(Long commentaireId);

    Commentaire getCommentaire(Long commentaireId);

    void supprimerLike(Long commentaireId);

    void supprimerDislike(Long commentaireId);

    Long getNombreCommentairesByRessourceId(Long id);
}
