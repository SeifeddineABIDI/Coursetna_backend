package tn.esprit.pidev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.pidev.entities.Notification;
import tn.esprit.pidev.entities.Ressource;
import tn.esprit.pidev.entities.User;

import java.util.List;

public interface INotificationRepository extends JpaRepository<Notification,Long> {
    List<Notification> findByDestinataire(User user);

    List<Notification> findByRessource(Ressource ressource);
}
