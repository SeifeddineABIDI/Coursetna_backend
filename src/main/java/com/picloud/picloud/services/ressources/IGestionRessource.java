package com.picloud.picloud.services.ressources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.picloud.picloud.entities.User;
import com.picloud.picloud.entities.ressources.Categorie;
import com.picloud.picloud.entities.ressources.Options;
import com.picloud.picloud.entities.ressources.Ressource;
import com.picloud.picloud.entities.ressources.Topic;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IGestionRessource {

    List<Ressource> getAll() throws JsonProcessingException;


//    List<Ressource> getRessourceByCategoryAndOption(Categorie categorie, Options option);
//    @Transactional
//    Ressource addRessource(MultipartFile file, Ressource ressource, Long userId) throws IOException;

//    @Transactional
//    Ressource addRessource(MultipartFile file, Ressource ressource, Long userId, Topic topic) throws IOException;

//    List<Ressource> getRessourceByCategory(Categorie categorie);

    List<Ressource> getRessourceByCategory(Categorie categorie, Long topicId);

    @Transactional
    Ressource addRessource(MultipartFile file, Ressource ressource, Long userId, String topicName) throws IOException;

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

    List<Ressource> getResourcesByUserId(Long userId);
}
