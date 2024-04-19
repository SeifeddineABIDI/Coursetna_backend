package tn.esprit.pidev.services.ressources;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.pidev.entities.ressources.Commentaire;
import tn.esprit.pidev.entities.ressources.Notification;
import tn.esprit.pidev.entities.ressources.Ressource;
import tn.esprit.pidev.entities.ressources.TypeNotif;
import tn.esprit.pidev.entities.user.User;
import tn.esprit.pidev.repository.ressources.ICommentaireRepository;
import tn.esprit.pidev.repository.ressources.IRessourceRepository;
import tn.esprit.pidev.repository.user.IUserRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class GestionCommentaireImpl implements IGestionCom {

    @Autowired
    private ICommentaireRepository commRepo;
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private IRessourceRepository ressourceRepo;
    @Autowired
    private GestionNotification notificationService;




    @Override
    @Transactional
    public Commentaire addComment(Commentaire comm, Integer userId, Long ressourceId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        Optional<Ressource> optionalRessource = ressourceRepo.findById(ressourceId);
        comm.setDatePublication(new Date());
        comm.setLikes(0);
        comm.setDislikes(0);
        comm.setEmotion(null);
        if (optionalUser.isPresent() && optionalRessource.isPresent()) {
            User user = optionalUser.get();
            Ressource ressource = optionalRessource.get();
            Optional<Commentaire> existingComment = commRepo.findByContenuAndRessources(comm.getContenu(), ressource);
            if (commRepo.existsByContenuAndRessourcesContaining(comm.getContenu(), ressource)) {
                throw new EntityExistsException("Ce commentaire existe déjà pour cette ressource.");
            }
            comm.getUtilisateurs().add(user);
            comm.getRessources().add(ressource);
            Commentaire savedComment = commRepo.save(comm);
            Notification notification = new Notification();
            notification.setMessage("Un nouveau commentaire a été ajouté à la ressource '" + ressource.getTitre() + "' : " + comm.getContenu());
            notification.setDateEnvoi(new Date());
            notification.setEstLue(false);
            notification.setCommentaire(savedComment);
            notification.setDestinataire(user);
            notification.setRessource(ressource);
            notification.setType(TypeNotif.COMMENTAIRE);
            notificationService.envoyerNotification(notification);

            return savedComment;
        } else {
            throw new EntityNotFoundException("Utilisateur ou ressource introuvable avec les IDs spécifiés");
        }
    }


    @Override
    public List<Commentaire> getCommentaireByRessourceId(Long ressourceId) {
        return commRepo.findByRessourcesId(ressourceId);
    }
    @Override
    public int calculerTotalLikes(Long commentaireId) {
        Commentaire commentaire = commRepo.findById(commentaireId).orElse(null);
        return commentaire.getLikes();
    }
    @Override
    public int calculerTotalDislikes(Long commentaireId) {
        Commentaire commentaire = commRepo.findById(commentaireId).orElse(null);
        return commentaire.getDislikes();
    }
    @Override
    public void ajouterLike(Long commentaireId) {
        Commentaire commentaire = commRepo.findById(commentaireId).orElse(null);

        commentaire.setLikes(commentaire.getLikes() + 1);
        commRepo.save(commentaire);
    }
   @Override
    public void ajouterDislike(Long commentaireId) {
        Commentaire commentaire = commRepo.findById(commentaireId).orElse(null);
        commentaire.setDislikes(commentaire.getDislikes() + 1);
        commRepo.save(commentaire);
    }
    @Override
    public void supprimerLike(Long commentaireId) {
        Commentaire commentaire = commRepo.findById(commentaireId).orElse(null);
        commentaire.setLikes(commentaire.getLikes() - 1);
        commRepo.save(commentaire);
    }
     @Override
    public void supprimerDislike(Long commentaireId) {
        Commentaire commentaire = commRepo.findById(commentaireId).orElse(null);
        commentaire.setDislikes(commentaire.getDislikes() - 1);
        commRepo.save(commentaire);
    }
}
