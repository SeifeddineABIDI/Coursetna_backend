package tn.esprit.pidev.services.evaluation;


import tn.esprit.pidev.entities.evaluation.Reponse;

import java.util.List;

public interface IGestionReponse {
    List<Reponse> retrieveAllReponses();
    Reponse retrieveReponse (Long numReponse);
    Reponse addReponse(Reponse reponse);
    Reponse updateReponse (Reponse reponse);
    void removeReponse(Long numReponse);


    Reponse addReponseAndAssignToQuestionAndUser(Reponse reponse,Long numQuestion,Long numUser);
    List<Reponse> getAllByUser (Long numUser);
    Reponse getReponseByUserAndQuestion (Long numUser,Long numQuestion);

}
