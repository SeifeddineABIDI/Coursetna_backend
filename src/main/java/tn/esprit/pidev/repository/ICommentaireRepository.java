package tn.esprit.pidev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.pidev.entities.Commentaire;
import tn.esprit.pidev.entities.Ressource;

import java.util.List;
import java.util.Optional;



public interface ICommentaireRepository extends JpaRepository<Commentaire,Long> {



    List<Commentaire> findByRessourceId(Long ressourceId);

    Long countByRessourceId(Long id);
}

