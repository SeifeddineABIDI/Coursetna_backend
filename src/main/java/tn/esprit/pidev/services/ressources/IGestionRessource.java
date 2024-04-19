package tn.esprit.pidev.services.ressources;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.pidev.entities.ressources.Categorie;
import tn.esprit.pidev.entities.ressources.Options;
import tn.esprit.pidev.entities.ressources.Ressource;
import tn.esprit.pidev.entities.user.User;

import java.io.IOException;
import java.util.List;

public interface IGestionRessource {

    List<Ressource> getAll() throws JsonProcessingException;
    List<Ressource> getRessourceByCategory(Categorie categorie, Long topicId);

    @Transactional
    Ressource addRessource(MultipartFile file, Ressource ressource, Integer userId, String topicName) throws IOException;

    @Transactional
    Ressource updateRessource(Long ressourceId, Ressource updatedRessource, MultipartFile newFile) throws IOException;

    List<Ressource> getResourcesForOption(Options option);

    List<User> determineUsersToNotify(Ressource ressource);


    Ressource getById(Long id);

    void  removeRessource(Long id);


    Page<Ressource> getAllWithPagination(int page, int size);


    List<Ressource> searchRessourcesByTitre(String titre);

    List<Ressource> filterRessourcesByTitre(String titre);

    void updateRatingForRessource(Long ressourceId, int newRating);


    @Transactional
    void rateRessource(Long id, int newRating);


    @Transactional
    void addRating(Long id, int rating);

    void archiverRessource(Long id) throws Exception;

    void désarchiverRessource(Long id) throws Exception;

    int countResourcesByTopicId(Long topicId);

    List<Ressource> getResourcesByUserId(Integer userId);
}
