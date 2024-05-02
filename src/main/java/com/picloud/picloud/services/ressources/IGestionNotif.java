package com.picloud.picloud.services.ressources;

import com.picloud.picloud.entities.User;
import com.picloud.picloud.entities.ressources.Notification;

import java.util.List;

public interface IGestionNotif {


    List<Notification> récupérerNotificationsUtilisateur(User utilisateur);

    List<Notification> getAll();

    void marquerCommeLue(Notification notification);


    void envoyerNotification(Notification notification);

    List<Notification> getNotificationsByUser(User user);
}
