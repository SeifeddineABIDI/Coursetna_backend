package tn.esprit.pidev.repository.stage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.pidev.entities.stage.Stage;

@Repository
public interface StageReposetory extends JpaRepository <Stage,Integer> {
}
