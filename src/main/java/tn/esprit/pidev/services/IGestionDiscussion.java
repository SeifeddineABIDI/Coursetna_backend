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
    public Discussion startDiscussionDuo(int userStart, int userEnd);

    public Discussion startDiscussionGroup(int userStart, String title, String userList, String image);

    public Discussion startDiscussionCommunity(int userStart, String title, String userList, String discussionList, String image);

    public ResponseEntity<String> modifyDiscussionGroup(Long discussion, int userStart, String title, String userList, int admin, String image);

    public ResponseEntity<String> modifyDiscussionCommunity(Long discussion, int userStart, String title, String userList, String discussionList, int admin, String image);

    public ResponseEntity<String> addAdminsToDiscussion(Long discussion, int admin, String userList);

    public List<Discussion> retrieveAllDiscussions(Integer id);

    public List<Discussion> retrieveAllCommunities(int id);

    public Discussion leaveDiscussion(int user, Long discussion);

    public ResponseEntity<String> deleteDiscussion(int user, Long discussion);

}
