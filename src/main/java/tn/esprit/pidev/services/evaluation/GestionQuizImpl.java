package tn.esprit.pidev.services.evaluation;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.pidev.entities.evaluation.Quiz;
import tn.esprit.pidev.repository.evaluation.IQuizRepository;

import java.util.List;

@AllArgsConstructor
@Service
public class GestionQuizImpl implements IGestionQuiz{
    IQuizRepository quizRepo;
    @Override
    public List<Quiz> retrieveAllQuizs() {
        return quizRepo.findAll();
    }

    @Override
    public Quiz retrieveQuiz(Long numQuiz) {
        return quizRepo.findById(numQuiz).get();
    }

    @Override
    public Quiz addQuiz(Quiz quiz) {
        return quizRepo.save(quiz);
    }

    @Override
    public Quiz updateQuiz(Quiz quiz) {
        return quizRepo.save(quiz);
    }

    @Override
    public void removeQuiz(Long numQuiz) {
        quizRepo.deleteById(numQuiz);
    }
}
