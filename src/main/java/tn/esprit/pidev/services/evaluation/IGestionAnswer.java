package tn.esprit.pidev.services.evaluation;


import tn.esprit.pidev.entities.evaluation.Answer;

import java.util.List;

public interface IGestionAnswer {
    List<Answer> retrieveAllReponses();
    Answer retrieveReponse (Long numReponse);
    Answer addReponse(Answer reponse);
    Answer updateReponse (Answer reponse);
    void removeReponse(Long numReponse);


    Answer addReponseAndAssignToQuestionAndUser(Answer reponse,Long numQuestion,Integer numUser);
    List<Answer> getAllByUser (Integer numUser);
    Answer getReponseByUserAndQuestion (Integer numUser,Long numQuestion);

}
