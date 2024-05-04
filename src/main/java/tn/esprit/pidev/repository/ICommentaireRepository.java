package tn.esprit.pidev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.pidev.entities.Commentaire;
import tn.esprit.pidev.entities.Ressource;

import java.util.List;
import java.util.Optional;

public interface ICommentaireRepository extends JpaRepository<Commentaire,Long> {


    List<Commentaire> findByRessourcesId(Long ressourceId);

    Optional<Commentaire> findByContenuAndRessources(String contenu, Ressource ressource);

    boolean existsByContenuAndRessourcesContaining(String contenu, Ressource ressource);
}
