package com.picloud.picloud.services.ressources;

import com.picloud.picloud.entities.User;
import com.picloud.picloud.entities.ressources.Notification;

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
