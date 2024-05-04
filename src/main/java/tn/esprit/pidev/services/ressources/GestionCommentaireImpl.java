package tn.esprit.pidev.services.ressources;

import tn.esprit.pidev.entities.User;
import tn.esprit.pidev.entities.ressources.Commentaire;
import tn.esprit.pidev.entities.ressources.Notification;
import tn.esprit.pidev.entities.ressources.Ressource;
import tn.esprit.pidev.entities.ressources.TypeNotif;
import tn.esprit.pidev.repository.IUserRepository;
import tn.esprit.pidev.repository.ressources.ICommentaireRepository;
import tn.esprit.pidev.repository.ressources.IRessourceRepository;
import jakarta.transaction.Transactional;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URLEncoder;
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
    public List<Commentaire> getCommentaireByRessourceId(Long ressourceId) {
        return commRepo.findByRessourceId(ressourceId);
    }
    @Override
    @Transactional
    public ResponseEntity<?> addCommennt(Commentaire comm) {
        boolean containsProfanity = filtrerBadWords(comm.getContenu());
        if (containsProfanity) {
            String errMsg = "Profanity exists: " + comm.getContenu();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errMsg);

        }
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
        return ResponseEntity.status(HttpStatus.CREATED).body(nouvelleComm);

    }

    public boolean filtrerBadWords(String content) {
        try {
            String encodedContent = URLEncoder.encode(content, "UTF-8");
            String url = "https://www.purgomalum.com/service/containsprofanity?text=" + encodedContent;
            try (CloseableHttpClient client = HttpClients.createDefault()) {
                HttpGet request = new HttpGet(url);
                String response = EntityUtils.toString(client.execute(request).getEntity());

                return Boolean.parseBoolean(response);
            }
        } catch (IOException e) {
            e.printStackTrace();

            return false;
        }
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
        if (commentaire != null && commentaire.getLikes() == 0) {
            commentaire.setLikes(1);
            commentaire.setDislikes(0); // Réinitialiser les dislikes à zéro
            commRepo.save(commentaire);
        }
    }
    @Override
    public void ajouterDislike(Long commentaireId) {
        Commentaire commentaire = commRepo.findById(commentaireId).orElse(null);
        if (commentaire != null) {
            commentaire.setLikes(0); // Réinitialiser les likes à zéro
            commentaire.setDislikes(1);
            commRepo.save(commentaire);
        }
    }
    @Override
    public Commentaire getCommentaire(Long commentaireId) {
        Optional<Commentaire> optionalCommentaire = commRepo.findById(commentaireId);
        return optionalCommentaire.orElse(null);
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
