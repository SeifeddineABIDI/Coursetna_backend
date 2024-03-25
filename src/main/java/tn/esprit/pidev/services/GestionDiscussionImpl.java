package tn.esprit.pidev.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.pidev.entities.Discussion;
import tn.esprit.pidev.entities.Message;
import tn.esprit.pidev.entities.TypeDiscussion;
import tn.esprit.pidev.entities.User;
import tn.esprit.pidev.repository.IDiscussionRepository;
import tn.esprit.pidev.repository.IUserRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class GestionDiscussionImpl implements IGestionDiscussion {
    @Autowired
    IDiscussionRepository iDiscussionRepository;
    @Autowired
    IUserRepository iUserRepository;
    @Override
    public Discussion startDiscussionDuo(Long userStart, Long userEnd) {
        Discussion discussion = new Discussion();
        discussion.setTypeDiscussion(TypeDiscussion.Duo);
        discussion.getUsers().add(iUserRepository.findById(userStart).get());
        discussion.getUsers().add(iUserRepository.findById(userEnd).get());
        discussion.setDateStart(LocalDateTime.now());
        discussion.setArchived(false);
        return iDiscussionRepository.save(discussion) ;
    }

    @Override
    public Discussion startDiscussionGroup(Long userStart, List<Long> userList) {
        Discussion discussion = new Discussion();
        discussion.setTypeDiscussion(TypeDiscussion.Group);
        discussion.getUsers().add(iUserRepository.findById(userStart).get());
        for (Long user : userList) {
            discussion.getUsers().add(iUserRepository.findById(user).get());
        }
        discussion.setDateStart(LocalDateTime.now());
        discussion.setArchived(false);
        return iDiscussionRepository.save(discussion) ;
    }

    @Override
    public Discussion startDiscussionCommunity(Long userStart, List<Long> userList) {
        Discussion discussion = new Discussion();
        discussion.setTypeDiscussion(TypeDiscussion.Community);
        discussion.getUsers().add(iUserRepository.findById(userStart).get());
        for (Long user : userList) {
            discussion.getUsers().add(iUserRepository.findById(user).get());
        }
        discussion.setDateStart(LocalDateTime.now());
        discussion.setArchived(false);
        return iDiscussionRepository.save(discussion) ;
    }

    public List<Message> retrieveAllMessages(Long id){
        return iDiscussionRepository.findById(id).get().getMessages();
    }

    @Override
    public Discussion addUserToDiscussion(Long id, List<Long> userList) {
        Discussion discussion = iDiscussionRepository.findById(id).get();
        for (Long user : userList) {
            discussion.getUsers().add(iUserRepository.findById(user).get());
        }
        return iDiscussionRepository.save(discussion) ;
    }

    @Override
    public boolean deleteDiscussion(Long id) {
        Discussion discussion = iDiscussionRepository.findById(id).get() ;
        discussion.setArchived(true);
        try {
            iDiscussionRepository.save(discussion);
            return true ;
        } catch(Exception e) {
            return false ;
        }

    }
}
