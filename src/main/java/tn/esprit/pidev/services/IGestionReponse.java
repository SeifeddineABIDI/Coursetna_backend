package tn.esprit.pidev.services;

import tn.esprit.pidev.entities.Reponse;

import java.util.List;

public interface IGestionReponse {
    List<Reponse> retrieveAllReponses();

    Reponse addReponse(Reponse reponse);

    Reponse updateReponse(Reponse reponse);

    Reponse RetrieveReponse(long idrep);
    void deleteReponse(long idrep);
    //Reponse addReponseAndAssignToReclamationAndUser(Reponse reponse, long numReclamtion, long numUser);
    Reponse addReponseAndAssignToReclamtion(Reponse reponse,long idrep);

    public String AddReponseAndAssignToReclamtion(Reponse reponse, long idrep) ;

    }


