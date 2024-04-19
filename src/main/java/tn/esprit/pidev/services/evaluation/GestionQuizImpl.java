package tn.esprit.pidev.services.evaluation;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.pidev.entities.evaluation.Quiz;
import tn.esprit.pidev.entities.ressources.Topic;
import tn.esprit.pidev.repository.evaluation.IQuizRepository;
import tn.esprit.pidev.repository.ressources.ItopicRepository;

import java.util.List;

@AllArgsConstructor
@Service
public class GestionQuizImpl implements IGestionQuiz{
    IQuizRepository quizRepo;

    ItopicRepository topicRepo;
    @Override
    public List<Quiz> retrieveAllQuizs() {
        return quizRepo.findAll();
    }

    @Override
    public Quiz retrieveQuiz(Long numQuiz) {
        return quizRepo.findById(numQuiz).get();
    }

    @Override
    public Quiz updateQuiz(Quiz quiz) {
        return quizRepo.save(quiz);
    }

    @Override
    public void removeQuiz(Long numQuiz) {
        quizRepo.deleteById(numQuiz);
    }

    @Override
    public Quiz addQuizAndAssignToTopic(Quiz quiz, Long numTopic){
        Topic topic=topicRepo.findById(numTopic).get();
        topic.getListQuiz().add(quiz);

        return quizRepo.save(quiz);
    }

}
