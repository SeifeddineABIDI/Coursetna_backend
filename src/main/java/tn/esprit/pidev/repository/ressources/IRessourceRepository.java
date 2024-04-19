package tn.esprit.pidev.repository.ressources;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tn.esprit.pidev.entities.ressources.Categorie;
import tn.esprit.pidev.entities.ressources.Options;
import tn.esprit.pidev.entities.ressources.Ressource;
import tn.esprit.pidev.entities.ressources.Topic;

import java.util.List;
@Repository
public interface IRessourceRepository extends JpaRepository<Ressource,Long> {

    List<Ressource> findByTitreContaining(String titre);
    @Query("SELECT DISTINCT r FROM Ressource r LEFT JOIN FETCH r.notifications")
    List<Ressource> findAllWithNotifications();
    List<Ressource> findByOptions(Options option);

    List<Ressource> findByTopic(Topic topic);

    List<Ressource> findByTopicId(Long topicId);

    List<Ressource> findByAuteurId(Integer userId);

    List<Ressource> getRessourceByCategorieAndTopicId(Categorie categorie, Long topicId);

}
