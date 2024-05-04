package tn.esprit.pidev.repository.ressources;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.pidev.entities.ressources.Badge;

public interface IBadgeRepo extends JpaRepository<Badge,Long> {
    Badge findByNom(String badgeNom);
}
