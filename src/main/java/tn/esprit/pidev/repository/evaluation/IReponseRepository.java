package tn.esprit.pidev.repository.evaluation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tn.esprit.pidev.entities.evaluation.Reponse;

import java.util.List;

@Repository
public interface IReponseRepository extends JpaRepository<Reponse,Long> {
    @Query("select r from Reponse r where r.user.id=:numUser")
    public List<Reponse> getAllanswersByUser(@Param("numUser") Long numU);

    @Query("select r from Reponse r where r.user.id=:numUser and r.question.numQuestion=:numQuestion")
    public Reponse getAnswerByUserAndQuestion(@Param("numUser") Long numU,@Param("numQuestion") Long numQ);
}
