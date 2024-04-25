package tn.esprit.pidev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tn.esprit.pidev.entities.Subforum;
import tn.esprit.pidev.entities.Post;
import tn.esprit.pidev.entities.User;

import java.util.List;

@Repository
public interface IPostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllBySubforum(Subforum subforum);

    List<Post> findByUser(User user);
    @Query("SELECT p, COUNT(c) AS commentCount FROM Post p JOIN p.comments c GROUP BY p ORDER BY COUNT(c) DESC")
    List<Object[]> findMostInteractivePosts();




}
