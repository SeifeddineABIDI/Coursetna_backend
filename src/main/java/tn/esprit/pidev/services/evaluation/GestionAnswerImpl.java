package tn.esprit.pidev.services.evaluation;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.pidev.entities.evaluation.Answer;
import tn.esprit.pidev.entities.evaluation.Question;
import tn.esprit.pidev.entities.User;
import tn.esprit.pidev.repository.evaluation.IAnswerRepository;
import tn.esprit.pidev.repository.evaluation.IQuestionRepository;
import tn.esprit.pidev.repository.IUserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@Service
public class GestionAnswerImpl implements IGestionAnswer {
    IAnswerRepository reponseRepo;
    IUserRepository userRepo;
    IQuestionRepository questionRepo;
    @Override
    public List<Answer> retrieveAllReponses() {
        return reponseRepo.findAll();
    }

    @Override
    public Answer retrieveReponse(Long numReponse) {
        return reponseRepo.findById(numReponse).get();
    }

    @Override
    public Answer updateReponse(Answer reponse) {
        return reponseRepo.save(reponse);
    }

    @Override
    public void removeReponse(Long numReponse) {
        reponseRepo.deleteById(numReponse);
    }

    /**********************END CRUD*******************************************/
    @Override
    public  Answer addReponseAndAssignToQuestionAndUser(Answer reponse,Long numQuestion,Integer numUser){
        User user= userRepo.findById(numUser).get();
        Question question= questionRepo.findById(numQuestion).get();

        reponse.setQuestion(question);
        reponse.setUser(user);
        return reponseRepo.save(reponse);
    }

    @Override
    public List<Answer> getAllByUser (Integer numUser){
        return reponseRepo.getAllanswersByUser(numUser);
    }
    @Override
    public List<Answer> getAnswersByUserAndQuiz (Integer numUser,Long numQuiz){
        return reponseRepo.getAllAnswerByUserAndQuiz(numUser,numQuiz);
    }
    /***************stat*********************************/
    public int getTotalCorrectAnswersForQuestion(Long numquestion) {
        List<Answer> answers = reponseRepo.findByQuestionn(numquestion);
        Question question=questionRepo.findById(numquestion).get();

        int totalCorrectAnswers = 0;
        for (Answer answer : answers) {
            if (answer.getSelectedChoice().equals(question.getCorrectAnswer())) {
                totalCorrectAnswers++;
            }
        }
        return totalCorrectAnswers;
    }
    public int getTotalAnswersForQuestion(Long numquestion) {
        List<Answer> answers = reponseRepo.findByQuestionn(numquestion);
        return answers.size();
    }
    public int getTotalUsersAnsweredQuiz(Long numquiz) {
        List<Answer> answers = reponseRepo.findByQuizz(numquiz);
        Set<User> users = new HashSet<>();
        for (Answer answer : answers) {
            users.add(answer.getUser());
        }
        return users.size();
    }




}
