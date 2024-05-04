package tn.esprit.pidev.services;

import tn.esprit.pidev.entities.Reclamtion;

import java.util.List;

public interface IGestionReclamation {
List<Reclamtion>retrieveAllReclamation();
Reclamtion addReclamation(Reclamtion reclamtion);
Reclamtion updateReclamtion(Reclamtion reclamtion);

Reclamtion RetrieveReclamation(long idrec);
void deleteReclamation(long idrec);
/*addReclamtionAndAssignToUser*/
Reclamtion addReclamtionAndAssignToUser(Reclamtion reclamtion,Integer id);
Reclamtion getReclamationByUserandReponse(Integer id,long idrep);
List<Reclamtion> findAllReclamationsWithUserAndResponse();

//Reclamtion addReclamtionAndAssignToUserandTopic(Reclamtion reclamtion,Integer idUser,Long idTopic);


List<Reclamtion> getAllReclamationsWithDetails();

List<Reclamtion> getAllDetails(Reclamtion reclamtion,Integer idUser,Long idTopic );

public String addReclamationAndAssignToUserAndTopic(Reclamtion reclamation, Integer idUser, Long idTopic);

//public String updateReclamationByUserAndReclamationId(Integer userId, Long reclamationId, Reclamtion updatedReclamation);
public String updateReclamationByUserAndReclamationId(Integer userId, Long reclamationId, Reclamtion updatedReclamation) ;
List<Reclamtion> getReclamationsByUserId(Integer userId);

    void deleteReclamationByUserAndReclamationId(Integer userId, Long reclamationId);
    public String deleteReclamationbyID(long idrec) ;

    public List<Reclamtion> getAllReclamationsWithResponses();
    public List<Reclamtion> findAllReclamationsWithResponses();

    public void deleteUnattendedReclamations();

    public List<Reclamtion> rechercherReclamationsAvancee(String titre, String description, String status) ;

    public long countReclamationsTraitees();
    public long countReclamationsNonTraitees();

    }
