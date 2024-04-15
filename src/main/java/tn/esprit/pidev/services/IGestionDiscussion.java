package tn.esprit.pidev.services;

import org.springframework.beans.factory.annotation.Autowired;
import tn.esprit.pidev.entities.Discussion;
import tn.esprit.pidev.entities.Message;
import tn.esprit.pidev.entities.User;
import tn.esprit.pidev.repository.IDiscussionRepository;
import tn.esprit.pidev.repository.IUserRepository;

import java.util.List;

public interface IGestionDiscussion {
    public Discussion startDiscussionDuo(Long userStart, Long userEnd);

    public Discussion startDiscussionGroup(Long userStart, String title, List<Long> userList);

    public Discussion startDiscussionCommunity(Long userStart, String title, List<Long> userList, String discussionList);

    public Discussion addUserToDiscussion(Long id, List<Long> userList);

    public Discussion addDiscussionToCommunity(Long id, String discussionList);

    public List<Discussion> retrieveAllDiscussions(Long id);

    public List<Discussion> retrieveAllCommunities(Long id);

    public Discussion renameDiscussion(Long id,String title);

    public boolean deleteDiscussion(Long id);
}
