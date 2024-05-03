package tn.esprit.pidev.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.pidev.entities.Forum;

@Repository
public interface ForumRepository extends JpaRepository<Forum, Long> {
    // Ajoutez des méthodes personnalisées si nécessaire
}
