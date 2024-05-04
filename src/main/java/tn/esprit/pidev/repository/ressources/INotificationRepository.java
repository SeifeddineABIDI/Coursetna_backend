package tn.esprit.pidev.repository.ressources;

import tn.esprit.pidev.entities.User;
import tn.esprit.pidev.entities.ressources.Notification;
import tn.esprit.pidev.entities.ressources.Ressource;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface INotificationRepository extends JpaRepository<Notification,Long> {
    List<Notification> findByDestinataire(User user);

    List<Notification> findByRessource(Ressource ressource);
}
