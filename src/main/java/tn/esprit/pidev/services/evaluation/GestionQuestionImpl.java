package tn.esprit.pidev.services.evaluation;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.pidev.entities.evaluation.Question;
import tn.esprit.pidev.repository.evaluation.IQuestionRepository;

import java.util.List;

@AllArgsConstructor
@Service
public class GestionQuestionImpl implements IGestionQuestion{
    IQuestionRepository questionRepo;
    @Override
    public List<Question> retrieveAllQuestions() {
        return questionRepo.findAll();
    }

    @Override
    public Question retrieveQuestion(Long numQuestion) {
        return questionRepo.findById(numQuestion).get();
    }

    @Override
    public Question addQuestion(Question question) {
        return questionRepo.save(question);
    }

    /*@Override
    public Question updateQuestion(Long numQuestion,Question question) {
        if(questionRepo.findById(numQuestion).get()!=null){
            question.setNumQuestion(numQuestion);
            return questionRepo.save(question);
        }
        return null;
    }*/
    @Override
    public Question updateQuestion(Question question) {
        return questionRepo.save(question);
    }

    @Override
    public void removeQuestion(Long numQuestion) {
        questionRepo.deleteById(numQuestion);
    }
}
