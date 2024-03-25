package tn.esprit.pidev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.pidev.entities.Message;

public interface IMessageRepository extends JpaRepository<Message, Long> {
}
