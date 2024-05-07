package tn.esprit.pidev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tn.esprit.pidev.entities.Score;

@Repository
public interface IScoreRepository extends JpaRepository<Score,Long> {
    @Query("select s from Score s where s.quiz.numQuiz=:idQ and s.user.id=:idU")
    public Score getScoreByUserAndQuiz(@Param("idU") Integer numUser,@Param("idQ") Long numQuiz);
}

