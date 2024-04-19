package tn.esprit.pidev.repository.evaluation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tn.esprit.pidev.entities.evaluation.Question;

import java.util.List;

@Repository
public interface IQuestionRepository extends JpaRepository<Question,Long> {
    @Query("select q from Question q where q.quiz.numQuiz=:id")
    public List<Question> getQuestionsByQuiz(@Param("id") Long numQuiz);
}
