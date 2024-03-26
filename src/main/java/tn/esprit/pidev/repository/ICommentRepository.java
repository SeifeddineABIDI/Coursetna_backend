package tn.esprit.pidev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.pidev.entities.Comment;
@Repository
public interface ICommentRepository extends JpaRepository<Comment, Long> {
}
