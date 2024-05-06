package tn.esprit.pidev.services.evaluation;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.pidev.entities.*;
import tn.esprit.pidev.entities.evaluation.Answer;
import tn.esprit.pidev.entities.evaluation.Question;
import tn.esprit.pidev.entities.evaluation.Quiz;
import tn.esprit.pidev.entities.evaluation.Score;
import tn.esprit.pidev.repository.*;
import tn.esprit.pidev.repository.evaluation.IAnswerRepository;
import tn.esprit.pidev.repository.evaluation.IQuestionRepository;
import tn.esprit.pidev.repository.evaluation.IQuizRepository;
import tn.esprit.pidev.repository.evaluation.IScoreRepository;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Service
public class GestionScoreImpl implements IGestionScore{
    IScoreRepository scoreRepo;
    IQuestionRepository questionRepo;
    IAnswerRepository reponseRepo;

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


    public Score calculateScore(Long numQuiz, Integer numUser) {
        Quiz quiz=quizRepo.findById(numQuiz).get();
        User user=userRepo.findById(numUser).get();

        List<Question>questions=quiz.getListQuestion();
        int totalQuestions = questions.size();
        int totalpts =0;
        double pts = 0;

        for (int i = 0; i < totalQuestions; i++) {
            Question question = questions.get(i);
            Answer answer = reponseRepo.getAnswerByUserAndQuestion(user.getId(), question.getNumQuestion());
            totalpts=totalpts+question.getPoints();

            if (answer.getSelectedChoice().equals(question.getCorrectAnswer()))
                pts = pts + question.getPoints();
        }
        double s=pts/totalpts*100;

        Score score=new Score();
        score.setScore(s);
        score.setDateTime(LocalDateTime.now());
        score.setUser(user);
        score.setQuiz(quiz);

        return scoreRepo.save(score);
        //return (double) score / totalQuestions * 100; // Assuming scoring out of 100
    }

    @Override
    public Score retrieveScoreByUserAndQuiz(Integer numUser,Long numQuiz){
        return scoreRepo.getScoreByUserAndQuiz(numUser,numQuiz);
    }

////////Stat//////////////////////
public double getAverageScore() {
    List<Score> scores = scoreRepo.findAll();
    if (scores.isEmpty()) {
        return 0.0;
    }
    double totalScore = scores.stream().mapToDouble(Score::getScore).sum();
    return totalScore / scores.size();
}
}
