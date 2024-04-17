package com.picloud.picloud.services.ressources;

import com.picloud.picloud.entities.ressources.Commentaire;
import com.picloud.picloud.entities.ressources.Ressource;
import jakarta.transaction.Transactional;

import java.util.List;

public interface IGestionCom {




    @Transactional
    Commentaire addComment(Commentaire comm, Long userId, Long ressourceId);


    List<Commentaire> getCommentaireByRessourceId(Long ressourceId);

    int calculerTotalLikes(Long commentaireId);

    int calculerTotalDislikes(Long commentaireId);

    void ajouterLike(Long commentaireId);

    void ajouterDislike(Long commentaireId);

    void supprimerLike(Long commentaireId);

    void supprimerDislike(Long commentaireId);
}
