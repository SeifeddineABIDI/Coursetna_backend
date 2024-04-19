package tn.esprit.pidev.repository.ressources;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.pidev.entities.ressources.Notification;
import tn.esprit.pidev.entities.ressources.Ressource;
import tn.esprit.pidev.entities.user.User;

import java.util.List;

public interface INotificationRepository extends JpaRepository<Notification,Long> {
    List<Notification> findByDestinataire(User user);

    List<Notification> findByRessource(Ressource ressource);
}
