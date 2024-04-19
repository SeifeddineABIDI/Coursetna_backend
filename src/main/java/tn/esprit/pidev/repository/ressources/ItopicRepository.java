package tn.esprit.pidev.repository.ressources;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tn.esprit.pidev.entities.ressources.Topic;

import java.util.List;

@Repository
public interface ItopicRepository extends JpaRepository<Topic,Long> {



    @Query("SELECT t.nom FROM Topic t")
    List<String> findAllNames();

    Topic findByNom(String nom);
}
