package tn.esprit.pidev.services.evaluation;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.pidev.entities.evaluation.Answer;
import tn.esprit.pidev.entities.evaluation.Question;
import tn.esprit.pidev.entities.user.User;
import tn.esprit.pidev.repository.evaluation.IAnswerRepository;
import tn.esprit.pidev.repository.evaluation.IQuestionRepository;
import tn.esprit.pidev.repository.user.IUserRepository;

import java.util.List;

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
    public Answer addReponse(Answer reponse) {
        return reponseRepo.save(reponse);
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
    public Answer getReponseByUserAndQuestion (Integer numUser,Long numQuestion){
        return reponseRepo.getAnswerByUserAndQuestion(numUser,numQuestion);
    }

}
