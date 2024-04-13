package tn.esprit.pidev.services.evaluation;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.pidev.entities.User;
import tn.esprit.pidev.entities.evaluation.Question;
import tn.esprit.pidev.entities.evaluation.Quiz;
import tn.esprit.pidev.entities.evaluation.Reponse;
import tn.esprit.pidev.entities.evaluation.Score;
import tn.esprit.pidev.repository.IUserRepository;
import tn.esprit.pidev.repository.evaluation.IQuestionRepository;
import tn.esprit.pidev.repository.evaluation.IQuizRepository;
import tn.esprit.pidev.repository.evaluation.IReponseRepository;
import tn.esprit.pidev.repository.evaluation.IScoreRepository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@AllArgsConstructor
@Service
public class GestionScoreImpl implements IGestionScore{
    IScoreRepository scoreRepo;
    IQuestionRepository questionRepo;
    IReponseRepository reponseRepo;

    IQuizRepository quizRepo;
    IUserRepository userRepo;
    @Override
    public List<Score> retrieveAllScores() {
        return scoreRepo.findAll();
    }

    @Override
    public Score retrieveScore(Long numScore) {
        return scoreRepo.findById(numScore).get();
    }


    public Score calculateScore(Long numQuiz, Long numUser) {
        Quiz quiz=quizRepo.findById(numQuiz).get();
        User user=userRepo.findById(numUser).get();

        List<Question>questions=quiz.getListQuestion();
        int totalQuestions = questions.size();
        int pts = 0;

        for (int i = 0; i < totalQuestions; i++) {
            Question question = questions.get(i);
            Reponse answer = reponseRepo.getAnswerByUserAndQuestion(user.getId(), question.getNumQuestion());

            if (answer.getSelectedChoice().equals(question.getCorrectAnswer()))
                pts = pts + question.getPoints();
        }

        Score score=new Score();
        score.setScore(pts);
        score.setDateTime(LocalDateTime.now());
        score.setUser(user);
        score.setQuiz(quiz);

        return scoreRepo.save(score);
        //return (double) score / totalQuestions * 100; // Assuming scoring out of 100
    }


}
