package tn.esprit.pidev.repository.ressources;

import tn.esprit.pidev.entities.ressources.Commentaire;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ICommentaireRepository extends JpaRepository<Commentaire,Long> {



    List<Commentaire> findByRessourceId(Long ressourceId);

    Long countByRessourceId(Long id);
}
