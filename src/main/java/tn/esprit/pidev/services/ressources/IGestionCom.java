package tn.esprit.pidev.services.ressources;

import jakarta.transaction.Transactional;
import tn.esprit.pidev.entities.ressources.Commentaire;

import java.util.List;

public interface IGestionCom {




    @Transactional
    Commentaire addComment(Commentaire comm, Integer userId, Long ressourceId);


    List<Commentaire> getCommentaireByRessourceId(Long ressourceId);

    int calculerTotalLikes(Long commentaireId);

    int calculerTotalDislikes(Long commentaireId);

    void ajouterLike(Long commentaireId);

    void ajouterDislike(Long commentaireId);

    void supprimerLike(Long commentaireId);

    void supprimerDislike(Long commentaireId);
}
