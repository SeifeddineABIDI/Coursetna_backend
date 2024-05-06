package tn.esprit.pidev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tn.esprit.pidev.entities.*;

import java.util.Date;
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

    Ressource findTopByOrderByIdDesc();

    int countByAuteurId(Long userId);
    @Query("SELECT COUNT(r) FROM Ressource r WHERE r.dateTlg BETWEEN :startOfWeek AND :endOfWeek")
    Long countRessourcesAddedByWeek(@Param("startOfWeek") Date startOfWeek, @Param("endOfWeek") Date endOfWeek);


    @Query("SELECT r.options, COUNT(r) FROM Ressource r GROUP BY r.options")
    List<Object[]> countRessourcesByOption();




}
