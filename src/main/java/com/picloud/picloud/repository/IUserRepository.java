package com.picloud.picloud.repository;

import com.picloud.picloud.entities.User;
import com.picloud.picloud.entities.ressources.Ressource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface IUserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.id <> :commentingUserId")
    List<User> findAllExceptCommentingUser(@Param("commentingUserId") Long commentingUserId);

}
