package tn.esprit.pidev.services.reclamation;

import tn.esprit.pidev.entities.reclamation.Reponse;

import java.util.List;

public interface IGestionReponse {
    List<Reponse> retrieveAllReponses();

    Reponse addReponse(Reponse reponse);

    Reponse updateReponse(Reponse reponse);

    Reponse RetrieveReponse(long idrep);
    void deleteReponse(long idrep);
    //Reponse addReponseAndAssignToReclamationAndUser(Reponse reponse, long numReclamtion, long numUser);
    Reponse addReponseAndAssignToReclamtion(Reponse reponse,long idrep);
}


