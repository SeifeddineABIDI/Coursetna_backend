package com.picloud.picloud.repository.ressources;

import com.picloud.picloud.entities.User;
import com.picloud.picloud.entities.ressources.Notification;
import com.picloud.picloud.entities.ressources.Ressource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface INotificationRepository extends JpaRepository<Notification,Long> {
    List<Notification> findByDestinataire(User user);

    List<Notification> findByRessource(Ressource ressource);
}
