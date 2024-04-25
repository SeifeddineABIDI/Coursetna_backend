package tn.esprit.pidev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.pidev.entities.Message;
import tn.esprit.pidev.entities.Reaction;
import tn.esprit.pidev.entities.User;

import java.util.Optional;

public interface IReactionRepository extends JpaRepository<Reaction, Long> {

   Optional<Reaction> findFirstByUserAndReactionAndMessage(User user, String reaction, Message message);
}
