package tn.esprit.pidev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tn.esprit.pidev.entities.Topic;
import tn.esprit.pidev.entities.Post;
import tn.esprit.pidev.entities.User;

import java.util.List;

@Repository
public interface IPostRepository extends JpaRepository<Post, Long> {


}
