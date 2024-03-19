package tn.esprit.pidev.services.evaluation;


import tn.esprit.pidev.entities.evaluation.Score;

import java.util.List;

public interface IGestionScore {
    List<Score> retrieveAllScores();
    Score retrieveScore (Long numScore);
    Score addScore(Score score);
    Score updateScore (Score score);
    void removeScore(Long numScore);
}
