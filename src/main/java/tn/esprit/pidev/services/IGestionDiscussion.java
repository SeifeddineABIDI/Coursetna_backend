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

    public Discussion startDiscussionCommunity(Long userStart,String title, List<Long> userList);

    public Discussion addUserToDiscussion(Long id, List<Long> userList);

    public List<Message> retrieveAllMessages(Long id);
    public boolean deleteDiscussion(Long id);
}
