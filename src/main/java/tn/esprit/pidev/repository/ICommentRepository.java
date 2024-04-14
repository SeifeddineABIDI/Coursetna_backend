package tn.esprit.pidev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.pidev.entities.Comment;
import tn.esprit.pidev.entities.Post;
import tn.esprit.pidev.entities.User;

import java.util.List;

@Repository
public interface ICommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPost(Post post);

    List<Comment> findAllByUser(User user);
}
