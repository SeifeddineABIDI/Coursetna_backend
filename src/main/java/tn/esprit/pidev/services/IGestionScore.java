package tn.esprit.pidev.services;


import tn.esprit.pidev.entities.Score;

import java.util.List;

public interface IGestionScore {
    List<Score> retrieveAllScores();
    Score retrieveScore (Long numScore);

    Score calculateScore(Long numQuiz, Integer numUser);
    Score retrieveScoreByUserAndQuiz(Integer numUser,Long numQuiz);

}
