package tn.esprit.pidev.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import tn.esprit.pidev.entities.Discussion;
import tn.esprit.pidev.entities.Message;
import tn.esprit.pidev.entities.User;
import tn.esprit.pidev.repository.IDiscussionRepository;
import tn.esprit.pidev.repository.IUserRepository;

import java.util.List;

public interface IGestionDiscussion {
    public Discussion startDiscussionDuo(Long userStart, Long userEnd);

    public Discussion startDiscussionGroup(Long userStart, String title, String userList, String image);

    public Discussion startDiscussionCommunity(Long userStart, String title, String userList, String discussionList, String image);

    public ResponseEntity<String> modifyDiscussionGroup(Long discussion, Long userStart, String title, String userList, Long admin, String image);

    public ResponseEntity<String> modifyDiscussionCommunity(Long discussion, Long userStart, String title, String userList, String discussionList, Long admin, String image);

    public ResponseEntity<String> addAdminsToDiscussion(Long discussion, Long admin, String userList);

    public List<Discussion> retrieveAllDiscussions(Long id);

    public List<Discussion> retrieveAllCommunities(Long id);

    public Discussion leaveDiscussion(Long user, Long discussion);

    public ResponseEntity<String> deleteDiscussion(Long user, Long discussion);
}
