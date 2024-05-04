package tn.esprit.pidev.services.ressources;

import tn.esprit.pidev.entities.User;
import tn.esprit.pidev.entities.ressources.Notification;

import java.util.List;

public interface IGestionNotif {


    List<Notification> récupérerNotificationsUtilisateur(User utilisateur);

    List<Notification> getAll();

    void marquerCommeLue(Notification notification);


    void envoyerNotification(Notification notification);

    List<Notification> getNotificationsByUser(User user);
}
