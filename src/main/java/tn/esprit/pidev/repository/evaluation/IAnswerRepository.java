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

    @Query("select r from Answer r where r.user.id=:numUser and r.question.numQuestion=:numQuestion")
    public Answer getAnswerByUserAndQuestion(@Param("numUser") Integer numU,@Param("numQuestion") Long numQ);
}
