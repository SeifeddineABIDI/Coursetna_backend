package tn.esprit.pidev.services.ressources;

import tn.esprit.pidev.entities.ressources.Notification;
import tn.esprit.pidev.entities.user.User;

import java.util.List;

public interface IGestionNotif {

     Notification add(Notification notification);

    List<Notification> récupérerNotificationsUtilisateur(User utilisateur);

    List<Notification> getAll();

    void marquerCommeLue(Notification notification);

    void supprimerNotification(Notification notification);

    void envoyerNotification(Notification notification);

    List<Notification> getNotificationsByUser(User user);
}
