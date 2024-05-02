package com.picloud.picloud.repository.ressources;

import com.picloud.picloud.entities.ressources.Commentaire;
import com.picloud.picloud.entities.ressources.Ressource;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ICommentaireRepository extends JpaRepository<Commentaire,Long> {



    List<Commentaire> findByRessourceId(Long ressourceId);

    Long countByRessourceId(Long id);
}
