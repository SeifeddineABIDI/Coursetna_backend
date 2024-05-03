package tn.esprit.pidev.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.pidev.entities.Stage;

@Repository
public interface StageReposetory extends JpaRepository <Stage,Integer> {
}
