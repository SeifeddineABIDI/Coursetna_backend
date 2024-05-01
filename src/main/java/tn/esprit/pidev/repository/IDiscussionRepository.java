package tn.esprit.pidev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tn.esprit.pidev.entities.Discussion;
import tn.esprit.pidev.entities.TypeDiscussion;
import tn.esprit.pidev.entities.User;

import java.util.List;

public interface IDiscussionRepository extends JpaRepository<Discussion, Long> {

    List<Discussion> findByUsersContainingAndArchivedIsFalseAndTypeDiscussionIsNot(User user, TypeDiscussion typeDiscussion);

    List<Discussion> findByUsersContainingAndArchivedIsFalseAndTypeDiscussionIs(User user, TypeDiscussion typeDiscussion);

    @Query("SELECT d FROM Discussion d WHERE :user1 MEMBER OF d.users AND :user2 MEMBER OF d.users AND d.typeDiscussion = :typeDiscussion")
    List<Discussion> findByTwoUsersAndTypeDiscussion(
            @Param("user1") User user1,
            @Param("user2") User user2,
            @Param("typeDiscussion") TypeDiscussion typeDiscussion
    );

}
