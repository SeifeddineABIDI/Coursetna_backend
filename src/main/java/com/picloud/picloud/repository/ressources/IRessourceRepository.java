package com.picloud.picloud.repository.ressources;

import com.picloud.picloud.entities.ressources.Categorie;
import com.picloud.picloud.entities.ressources.Options;
import com.picloud.picloud.entities.ressources.Ressource;
import com.picloud.picloud.entities.ressources.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface IRessourceRepository extends JpaRepository<Ressource,Long> {

    List<Ressource> findByTitreContaining(String titre);
    @Query("SELECT DISTINCT r FROM Ressource r LEFT JOIN FETCH r.notifications")
    List<Ressource> findAllWithNotifications();
    List<Ressource> findByOptions(Options option);

    List<Ressource> findByTopic(Topic topic);

    List<Ressource> findByTopicId(Long topicId);

    List<Ressource> findByAuteurId(Long userId);

    List<Ressource> getRessourceByCategorieAndTopicId(Categorie categorie, Long topicId);

}
