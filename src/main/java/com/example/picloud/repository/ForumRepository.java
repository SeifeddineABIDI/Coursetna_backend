package com.example.picloud.repository;

import com.example.picloud.entities.Forum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ForumRepository extends JpaRepository<Forum, Long> {
    // Ajoutez des méthodes personnalisées si nécessaire
}
