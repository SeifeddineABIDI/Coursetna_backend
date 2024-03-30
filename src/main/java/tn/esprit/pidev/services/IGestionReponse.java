package tn.esprit.pidev.services;

import tn.esprit.pidev.entities.Reclamtion;
import tn.esprit.pidev.entities.Reponse;
import tn.esprit.pidev.entities.User;

import java.util.List;

public interface IGestionReponse {
    List<Reponse> retrieveAllReponses();

    Reponse addReponse(Reponse reponse);

    Reponse updateReponse(Reponse reponse);

    Reponse RetrieveReponse(long idrep);
    void deleteReponse(long idrep);

    Reponse addReponseAndAssignToReclamationAndUser(Reponse reponse, Reclamtion reclamtion, User user);
}


