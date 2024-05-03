package tn.esprit.pidev.repository.evaluation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tn.esprit.pidev.entities.evaluation.Answer;

import java.util.List;

@Repository
public interface IAnswerRepository extends JpaRepository<Answer,Long> {
    @Query("select r from Answer r where r.user.id=:numUser")
    public List<Answer> getAllanswersByUser(@Param("numUser") Integer numU);

    @Query("select r from Answer r where r.user.id=:numUser and r.question.quiz.numQuiz=:numQuiz")
    public List<Answer> getAllAnswerByUserAndQuiz(@Param("numUser") Integer numU,@Param("numQuiz") Long numQ);

    //pour calculer score
    @Query("select r from Answer r where r.user.id=:numUser and r.question.numQuestion=:numQuest")
    public Answer getAnswerByUserAndQuestion(@Param("numUser") Integer numU,@Param("numQuest") Long numQ);

//////stat
@Query("select r from Answer r where r.question.numQuestion=:numQuest")
List<Answer> findByQuestionn(@Param("numQuest") Long Qusetion);
    @Query("select r from Answer r where r.question.quiz.numQuiz=:Q")
    public List<Answer> findByQuizz(@Param("Q") Long Q);

}
