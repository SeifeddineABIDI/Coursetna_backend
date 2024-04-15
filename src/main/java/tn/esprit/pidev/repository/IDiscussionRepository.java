package tn.esprit.pidev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.pidev.entities.Discussion;
import tn.esprit.pidev.entities.TypeDiscussion;
import tn.esprit.pidev.entities.User;

import java.util.List;

public interface IDiscussionRepository extends JpaRepository<Discussion, Long> {

    List<Discussion> findByUsersContainingAndArchivedIsFalseAndTypeDiscussionIsNot(User user, TypeDiscussion typeDiscussion);

    List<Discussion> findByUsersContainingAndArchivedIsFalseAndTypeDiscussionIs(User user, TypeDiscussion typeDiscussion);

}
