package tn.esprit.pidev.repository.evaluation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.pidev.entities.evaluation.Quiz;

@Repository
public interface IQuizRepository extends JpaRepository<Quiz,Long> {
}
