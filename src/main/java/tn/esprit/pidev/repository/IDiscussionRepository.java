package tn.esprit.pidev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.pidev.entities.Discussion;

public interface IDiscussionRepository extends JpaRepository<Discussion, Long> {
}
