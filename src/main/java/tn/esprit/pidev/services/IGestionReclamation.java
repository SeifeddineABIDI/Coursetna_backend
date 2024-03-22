package tn.esprit.pidev.services;

import tn.esprit.pidev.entities.Reclamtion;

import java.util.List;

public interface IGestionReclamation {
List<Reclamtion>retrieveAllReclamation();
Reclamtion addReclamation(Reclamtion reclamtion);
Reclamtion updateReclamtion(Reclamtion reclamtion);

Reclamtion RetrieveReclamation(long idrec);
void deleteReclamation(long idrec);


}
