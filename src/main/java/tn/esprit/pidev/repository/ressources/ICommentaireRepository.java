package tn.esprit.pidev.repository.ressources;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.pidev.entities.ressources.Commentaire;
import tn.esprit.pidev.entities.ressources.Ressource;

import java.util.List;
import java.util.Optional;

public interface ICommentaireRepository extends JpaRepository<Commentaire,Long> {


    List<Commentaire> findByRessourcesId(Long ressourceId);

    Optional<Commentaire> findByContenuAndRessources(String contenu, Ressource ressource);

    boolean existsByContenuAndRessourcesContaining(String contenu, Ressource ressource);
}
