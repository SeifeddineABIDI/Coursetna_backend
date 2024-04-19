package tn.esprit.pidev.services.ressources;


import jakarta.transaction.Transactional;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.pidev.entities.ressources.Categorie;
import tn.esprit.pidev.entities.ressources.Notification;
import tn.esprit.pidev.entities.ressources.*;
import tn.esprit.pidev.entities.user.User;
import tn.esprit.pidev.repository.ressources.*;
import tn.esprit.pidev.repository.user.IUserRepository;
import tn.esprit.pidev.services.user.IGestionUser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class GestionRessourceImpl implements IGestionRessource {

    @Autowired
    IRessourceRepository ressourceRepo;
    @Autowired
    IUserRepository userRepository;
    @Autowired
    IGestionNotif notifservice;
    @Autowired
    ItopicRepository topicRepository;
    @Autowired
    INotificationRepository notificationRepository;

    @Autowired
    IGestionUser userService;
    @Value("${upload.directory}")
    private String uploadDirectory;


    @Override
    public List<Ressource> getAll() {
        return ressourceRepo.findAll();
    }

    @Override
    public List<Ressource> getRessourceByCategory(Categorie categorie, Long topicId) {
        return ressourceRepo.getRessourceByCategorieAndTopicId(categorie,topicId);
    }


    @Override
    @Transactional
    public Ressource addRessource(MultipartFile file, Ressource ressource, Integer userId, String topicName) throws IOException {
        try {
            Optional<User> optionalUser = userRepository.findById(userId);
            if (!optionalUser.isPresent()) {
                throw new RuntimeException("L'utilisateur avec l'ID spécifié n'existe pas.");
            }
            Topic topic = topicRepository.findByNom(topicName);
            if (topic == null) {
                throw new RuntimeException("Le topic spécifié n'existe pas.");
            }
            String fileName = ressource.getTitre().replaceAll("\\s+", "_") + "." + FilenameUtils.getExtension(file.getOriginalFilename());
            String filePath = uploadDirectory + File.separator + fileName;
            Path path = Paths.get(filePath);
            Files.write(path, file.getBytes());
            ressource.setFilePath(filePath);
            ressource.setTailleFichier(file.getSize());
            String contentType = file.getContentType();
            User user = optionalUser.get();
            ressource.setAuteur(user);
            ressource.setTopic(topic);
            ressource.setTypeFichier(contentType);
            Date currentDate = new Date();
            ressource.setDateTlg(currentDate);
            ressource.setRating(0);
            ressource.setArchived(false);
            Ressource nouvelleRessource = ressourceRepo.save(ressource);
            sendNotifications(nouvelleRessource, currentDate);
            return nouvelleRessource;
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de l'ajout de la ressource", e);
        }
    }


    @Override
    @Transactional
    public Ressource updateRessource(Long ressourceId, Ressource updatedRessource, MultipartFile file) throws IOException {
        Optional<Ressource> optionalRessource = ressourceRepo.findById(ressourceId);
        if (!optionalRessource.isPresent()) {
            throw new RuntimeException("La ressource avec l'ID spécifié n'existe pas.");
        }
        Ressource existingRessource = optionalRessource.get();
        if (updatedRessource.getTitre() != null) {
            existingRessource.setTitre(updatedRessource.getTitre());
        }
        if (updatedRessource.getDescription() != null) {
            existingRessource.setDescription(updatedRessource.getDescription());
        }
        if (file != null) {
            Files.deleteIfExists(Paths.get(existingRessource.getFilePath()));
            String newFileName = file.getOriginalFilename();
            String newFilePath = uploadDirectory + File.separator + newFileName;
            Path newPath = Paths.get(newFilePath);
            Files.write(newPath, file.getBytes());
            existingRessource.setFilePath(newFilePath);
            existingRessource.setTailleFichier(file.getSize());
            existingRessource.setTypeFichier(file.getContentType());
        }
        Ressource updatedR = ressourceRepo.save(existingRessource);
        return updatedR;
    }

    @Override
    public List<Ressource> getResourcesForOption(Options option) {
        return ressourceRepo.findByOptions(option);
    }

    private void sendNotifications(Ressource ressource, Date currentDate) {

        List<User> utilisateurs = userRepository.findAll();
        for (User recipient : utilisateurs) {
            Notification notification = new Notification();
            notification.setDestinataire(recipient);
            notification.setMessage("Une nouvelle ressource a été ajouté à la ressource '" + ressource.getTitre());
            notification.setDateEnvoi(new Date());
            notification.setEstLue(false);
            notification.setRessource(ressource);
            notification.setType(TypeNotif.NOUVELLE_RESSOURCE);
            notifservice.envoyerNotification(notification);
        }
    }


    @Override
    public List<User> determineUsersToNotify(Ressource ressource) {
        List<User> usersWhoDownloadedSimilarResources = getUsersWhoDownloadedSimilarResources(ressource);
        return usersWhoDownloadedSimilarResources;
    }

    private List<User> getUsersWhoDownloadedSimilarResources(Ressource ressource) {
        return null;
    }



    @Override
    public Ressource getById(Long id) {
        return ressourceRepo.getById(id);
    }

    @Override
    public void removeRessource(Long id) {
        Ressource ressource = ressourceRepo.findById(id).orElse(null);
        if (ressource != null) {
            List<Notification> notifications = notificationRepository.findByRessource(ressource);
            for (Notification notification : notifications) {
                notificationRepository.delete(notification);
            }
            ressourceRepo.delete(ressource);
        }

    }

    @Override
    public Page<Ressource> getAllWithPagination(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ressourceRepo.findAll(pageable);
    }

    @Override
    public List<Ressource> searchRessourcesByTitre(String titre) {
        return ressourceRepo.findByTitreContaining(titre);
    }

    @Override
    public List<Ressource> filterRessourcesByTitre(String titre) {
        return ressourceRepo.findByTitreContaining(titre);
    }

    @Override
    public void updateRatingForRessource(Long ressourceId, int newRating) {
        Ressource ressource = ressourceRepo.findById(ressourceId).orElse(null);
        if (ressource != null) {
            Integer currentRating = ressource.getRating();
            int totalRatings = ressource.getNumberOfRatings();
            double newAverageRating;
            if (totalRatings == 0) {
                newAverageRating = newRating;
            } else {
                newAverageRating = ((currentRating * totalRatings) + newRating) / (totalRatings + 1);
            }
            ressource.setRating((int) newAverageRating);
            ressource.setNumberOfRatings(totalRatings + 1);
            ressourceRepo.save(ressource);
        }
    }
    @Override
    @Transactional
    public void rateRessource(Long id, int newRating) {
        Optional<Ressource> optionalRessource = ressourceRepo.findById(id);
        if (optionalRessource.isPresent()) {
            Ressource ressource = optionalRessource.get();
            ressource.setRating(newRating);
            ressourceRepo.save(ressource);
            updateRatingForRessource(id, newRating);
        } else {
            throw new RuntimeException("Ressource non trouvée avec l'ID spécifié");
        }
    }
    @Override
    @Transactional
    public void addRating(Long id, int rating) {
        Optional<Ressource> optionalRessource = ressourceRepo.findById(id);
        if (optionalRessource.isPresent()) {
            Ressource ressource = optionalRessource.get();
            ressource.setRating(rating);
            ressourceRepo.save(ressource);
            updateRatingForRessource(id, rating);
        } else {
            throw new RuntimeException("Ressource non trouvée avec l'ID spécifié");
        }
    }
    public List<Ressource> getRessourcesByTopicName(Topic topic) {
        return ressourceRepo.findByTopic(topic);
    }
    @Override
    public void archiverRessource(Long id) throws Exception {
        Optional<Ressource> optionalRessource = ressourceRepo.findById(id);
        if (optionalRessource.isPresent()) {
            Ressource ressource = optionalRessource.get();
            ressource.setArchived(true);
            ressourceRepo.save(ressource);
        } else {
            throw new Exception("La ressource avec l'identifiant " + id + " n'a pas été trouvée.");
        }
    }
    @Override
    public void désarchiverRessource(Long id) throws Exception {
        Optional<Ressource> optionalRessource = ressourceRepo.findById(id);
        if (optionalRessource.isPresent()) {
            Ressource ressource = optionalRessource.get();
            ressource.setArchived(false);
            ressourceRepo.save(ressource);
        } else {
            throw new Exception("La ressource avec l'identifiant " + id + " n'a pas été trouvée.");
        }
    }
    @Override
    public int countResourcesByTopicId(Long topicId) {
        List<Ressource> resources = ressourceRepo.findByTopicId(topicId);
        return resources.size();
    }
    @Override
    public List<Ressource> getResourcesByUserId(Integer userId) {
        return ressourceRepo.findByAuteurId(userId);
    }

}











