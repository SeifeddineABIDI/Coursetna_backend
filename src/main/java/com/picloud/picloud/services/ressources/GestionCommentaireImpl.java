package com.picloud.picloud.services.ressources;

import com.picloud.picloud.entities.User;
import com.picloud.picloud.entities.ressources.Commentaire;
import com.picloud.picloud.entities.ressources.Notification;
import com.picloud.picloud.entities.ressources.Ressource;
import com.picloud.picloud.entities.ressources.TypeNotif;
import com.picloud.picloud.repository.IUserRepository;
import com.picloud.picloud.repository.ressources.ICommentaireRepository;
import com.picloud.picloud.repository.ressources.IRessourceRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Date;
import java.util.List;

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
    public List<Commentaire> getCommentaireByRessourceId(Long ressourceId) {
        return commRepo.findByRessourceId(ressourceId);
    }
    @Override
    @Transactional
    public Commentaire addCommennt(Commentaire comm) {
        Commentaire nouvelleComm = commRepo.save(comm);
        User auteur = comm.getAuteur();
        Ressource ressource = comm.getRessource();
        comm.setDatePublication(new Date());
        comm.setLikes(0);
        comm.setDislikes(0);
        comm.setEmotion(null);
        if (ressource != null) {
            List<User> utilisateurs = userRepository.findAll();
            for (User utilisateur : utilisateurs) {
                if (!utilisateur.equals(auteur)) {
                    Notification notification = new Notification();
                    notification.setMessage("Un nouveau commentaire a été ajouté à la ressource '" + ressource.getTitre() + "' : " + comm.getContenu());
                    notification.setDateEnvoi(new Date());
                    notification.setEstLue(false);
                    notification.setCommentaire(nouvelleComm);
                    notification.setDestinataire(utilisateur);
                    notification.setRessource(ressource);
                    notification.setType(TypeNotif.COMMENTAIRE);
                    notificationService.envoyerNotification(notification);
                }
            }
        }
        return nouvelleComm;
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
    @Override
    public Long getNombreCommentairesByRessourceId(Long id) {
        return commRepo.countByRessourceId(id);
    }
}
