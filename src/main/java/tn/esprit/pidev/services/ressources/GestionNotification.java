package tn.esprit.pidev.services.ressources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.pidev.entities.ressources.Notification;
import tn.esprit.pidev.entities.user.User;
import tn.esprit.pidev.repository.ressources.INotificationRepository;

import java.util.List;
@Service
public class GestionNotification implements IGestionNotif {

    @Autowired
    private INotificationRepository notificationRepository;

    @Override
    public Notification add(Notification notification) {
        return notificationRepository.save(notification);
    }

    @Override
    public List<Notification> récupérerNotificationsUtilisateur(User utilisateur) {
        return notificationRepository.findByDestinataire(utilisateur);
    }

    @Override
    public List<Notification> getAll() {
        return notificationRepository.findAll();
    }

    @Override
    public void marquerCommeLue(Notification notification) {
        notification.setEstLue(true);
        notificationRepository.save(notification);
    }

    @Override
    public void supprimerNotification(Notification notification) {
        notificationRepository.delete(notification);
    }

    @Override
    public void envoyerNotification(Notification notification) {
        notificationRepository.save(notification);
        System.out.println("Notification sauvegardée : " + notification.getMessage());
        switch (notification.getType()) {
            case NOUVELLE_RESSOURCE:
                break;
            case COMMENTAIRE:
                break;
            case NOUVELLE_VERSION:
                break;
            default:
                break;
        }
    }


    @Override
    public List<Notification> getNotificationsByUser(User user) {
        return notificationRepository.findByDestinataire(user);
    }

}
