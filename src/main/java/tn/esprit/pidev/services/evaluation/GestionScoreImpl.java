package tn.esprit.pidev.services.evaluation;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.pidev.entities.evaluation.Score;
import tn.esprit.pidev.repository.evaluation.IScoreRepository;

import java.util.List;

@AllArgsConstructor
@Service
public class GestionScoreImpl implements IGestionScore{
    IScoreRepository scoreRepo;
    @Override
    public List<Score> retrieveAllScores() {
        return scoreRepo.findAll();
    }

    @Override
    public Score retrieveScore(Long numScore) {
        return scoreRepo.findById(numScore).get();
    }

    @Override
    public Score addScore(Score score) {
        return scoreRepo.save(score);
    }

    @Override
    public Score updateScore(Score score) {
        return scoreRepo.save(score);
    }

    @Override
    public void removeScore(Long numScore) {
        scoreRepo.deleteById(numScore);
    }
}
