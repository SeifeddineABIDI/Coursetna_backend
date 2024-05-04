package tn.esprit.pidev.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tn.esprit.pidev.entities.Reclamtion;
import tn.esprit.pidev.entities.Topic;
import tn.esprit.pidev.entities.TypeStatus;
import tn.esprit.pidev.entities.User;
import tn.esprit.pidev.repository.IReclamationRepository;
import tn.esprit.pidev.repository.IUserRepository;
import tn.esprit.pidev.repository.ItopicRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class GestionReclamationImpl implements IGestionReclamation {
    @Autowired
    IReclamationRepository iReclamationRepository;
    @Autowired
    IUserRepository iUserRepository;
    @Autowired
    ItopicRepository itopicRepository;
    @Override
    public List<Reclamtion> retrieveAllReclamation() {
        return iReclamationRepository.findAll();
    }

    @Override
    public Reclamtion addReclamation(Reclamtion reclamtion) {
        return iReclamationRepository.save(reclamtion);
    }

    @Override
    public Reclamtion updateReclamtion(Reclamtion reclamtion) {
        return iReclamationRepository.save(reclamtion);
    }

    @Override
    public Reclamtion RetrieveReclamation(long idrec) {
        return iReclamationRepository.findById(idrec).get() ;
    }

    @Override
    public void deleteReclamation(long idrec) {
        iReclamationRepository.deleteById(idrec);
    }

    @Override
    public Reclamtion addReclamtionAndAssignToUser(Reclamtion reclamtion, Integer id) {
        User user=iUserRepository.findById(id).get();
        reclamtion.setUser(user);
        return iReclamationRepository.save(reclamtion);

    }
    /*getReclamationByUserandReponse*/
    @Override
    public Reclamtion getReclamationByUserandReponse(Integer id, long idrep) {
        return iReclamationRepository.getReclamationByUserAndResponse(id,idrep);
    }

    @Override
    public List<Reclamtion> findAllReclamationsWithUserAndResponse() {
        return iReclamationRepository.findAllReclamationsWithUserAndResponse();
    }



   /* @Override
    public Reclamtion addReclamtionAndAssignToUserandTopic(Reclamtion reclamtion, Integer idUser, Long idTopic) {
        try {
            // Find the user and check if they exist
            User user = iUserRepository.findById(idUser)
                    .orElseThrow(() -> new NoSuchElementException("User not found with id: " + idUser));

            // Find the topic and check if it exists
            Topic topic = itopicRepository.findById(idTopic)
                    .orElseThrow(() -> new NoSuchElementException("Topic not found with id: " + idTopic));

            // Set user and topic to reclamation
            reclamtion.setUser(user);
            reclamtion.setTopic(topic);

            // Save and return reclamation
            Reclamtion savedReclamation = iReclamationRepository.save(reclamtion);
            System.out.println("Reclamation added successfully.");
            return savedReclamation;
        } catch (NoSuchElementException | DataAccessException e) {
            // Handle exceptions
            System.err.println("Failed to add reclamation: " + e.getMessage());
            return null;
        }
    }*/

    @Override
    public List<Reclamtion> getAllReclamationsWithDetails() {
        // Cette méthode appelle la méthode personnalisée du repository pour récupérer
        // toutes les réclamations avec leurs détails associés.
        return iReclamationRepository.findAllWithDetails();
    }

  /*  @Override
    public List<Reclamtion> getAllDetails(Reclamtion reclamtion, Integer idUser, Long idTopic) {
        User user =iUserRepository.findById(idUser).get();
        Topic topic= itopicRepository.findById(idTopic).get();

        return null;
    }*/
  @Override
  public List<Reclamtion> getAllDetails(Reclamtion reclamtion, Integer idUser, Long idTopic) {
      // Appelez la méthode du repository pour obtenir les réclamations par ID d'utilisateur et de sujet.
      return iReclamationRepository.findReclamationsByUserAndTopic(idUser, idTopic);
  }

    public String addReclamationAndAssignToUserAndTopic(Reclamtion reclamation, Integer idUser, Long idTopic) {
        try {
            // Find the user and check if they exist
            User user = iUserRepository.findById(idUser)
                    .orElseThrow(() -> new NoSuchElementException("User not found with id: " + idUser));

            // Find the topic and check if it exists
            Topic topic = itopicRepository.findById(idTopic)
                    .orElseThrow(() -> new NoSuchElementException("Topic not found with id: " + idTopic));

            // Set user and topic to reclamation
            reclamation.setUser(user);
            reclamation.setTopic(topic);

            // Save reclamation
            iReclamationRepository.save(reclamation);

            // Return success message
            return "Reclamation added successfully.";
        } catch (Exception e) {
            // Return error message
            return "Failed to add reclamation: " + e.getMessage();
        }
    }
    @Override
    public String updateReclamationByUserAndReclamationId(Integer userId, Long reclamationId, Reclamtion updatedReclamation) {
        try {
            // Find the user and check if they exist
            User user = iUserRepository.findById(userId)
                    .orElseThrow(() -> new NoSuchElementException("User not found with id: " + userId));

            // Find the reclamation and check if it exists
            Reclamtion reclamation = iReclamationRepository.findById(reclamationId)
                    .orElseThrow(() -> new NoSuchElementException("Reclamation not found with id: " + reclamationId));

            // Check if the user is the owner of the reclamation
            if (!reclamation.getUser().equals(user)) {
                return "User does not have permission to update this reclamation.";
            }

            // Update the reclamation object with the provided data
            // For example:
            reclamation.setDescription(updatedReclamation.getDescription());
            reclamation.setCreationDate(updatedReclamation.getCreationDate());
            // Update other fields as needed

            // Save the updated reclamation
            iReclamationRepository.save(reclamation);

            return "Reclamation updated successfully.";
        } catch (Exception e) {
            return "Failed to update reclamation: " + e.getMessage();
        }
    }

    @Override
    public List<Reclamtion> getReclamationsByUserId(Integer userId) {
        return iReclamationRepository.findByUserId(userId);
    }

    @Override
    public void deleteReclamationByUserAndReclamationId(Integer userId, Long reclamationId) {
        // Check if the reclamation exists and belongs to the user
        Reclamtion reclamation = iReclamationRepository.findByIdrecAndUserId(reclamationId, userId)
                .orElseThrow(() -> new NoSuchElementException("Reclamation not found for user: " + userId));

        // Delete the reclamation
        iReclamationRepository.delete(reclamation);
    }

    @Override
    public String deleteReclamationbyID(long idrec) {
        try {
            iReclamationRepository.deleteById(idrec);
            return "Reclamation deleted successfully.";
        } catch (EmptyResultDataAccessException e) {
            return "Reclamation not found with ID: " + idrec;
        } catch (Exception e) {
            return "Failed to delete reclamation: " + e.getMessage();
        }
    }

    @Override
    public List<Reclamtion> getAllReclamationsWithResponses() {
        return iReclamationRepository.findAllWithResponses();
    }
    @Override
    public List<Reclamtion> findAllReclamationsWithResponses() {
        return iReclamationRepository.findAllWithResponses();
    }

    @Scheduled(fixedDelay = 600000) // Runs every 1 minute 6000(in milliseconds)
    public void deleteUnattendedReclamations() {
        // Calculate the timestamp 1 minute ago
        LocalDateTime oneMinuteAgo = LocalDateTime.now().minusMinutes(1);

        // Find unattended reclamations with status "non_traite" and created more than 1 minute ago
        List<Reclamtion> unattendedReclamations = iReclamationRepository.findUnattendedReclamationsWithTypeStatus(oneMinuteAgo, TypeStatus.non_traite);

        // Delete unattended reclamations
        iReclamationRepository.deleteAll(unattendedReclamations);

        // You can add logging or additional actions here if needed
    }

    public List<Reclamtion> rechercherReclamationsAvancee(String titre, String description, String status) {
        return iReclamationRepository.findByTitreContainingAndDescriptionContainingAndStatus(titre, description, status);
    }

     public long countReclamationsTraitees() {
        return iReclamationRepository.countByStatus(TypeStatus.traite);
    }

    public long countReclamationsNonTraitees() {
        return iReclamationRepository.countByStatus(TypeStatus.non_traite);
    }

}
