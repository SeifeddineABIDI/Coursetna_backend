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
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
        User userEndo = iUserRepository.findById(userEnd).get() ;
        discussion.getUsers().add(iUserRepository.findById(userStart).get());
        discussion.getUsers().add(userEndo);
        discussion.setTitle(userEndo.getPrenom() + " " + userEndo.getNom());
        discussion.setDateStart(LocalDateTime.now());
        discussion.setArchived(false);
        return iDiscussionRepository.save(discussion) ;
    }

    @Override
    public Discussion startDiscussionGroup(Long userStart, String title, String userList, String image) {
        Discussion discussion = new Discussion();
        discussion.setTypeDiscussion(TypeDiscussion.Group);
        discussion.setTitle(title);

        discussion.getUsers().add(iUserRepository.findById(userStart).get());

        if (userList != null && !userList.isEmpty()) {
            String[] userIdStrings = userList.split("_");

            for (String userIdString : userIdStrings) {
                Long userId = Long.parseLong(userIdString);
                if (iUserRepository.existsById(userId)) {
                    discussion.getUsers().add(iUserRepository.findById(userId).get());
                }
            }
        }

        discussion.setDateStart(LocalDateTime.now());
        discussion.setArchived(false);
        discussion.setPhoto(image);

        return iDiscussionRepository.save(discussion);
    }

    @Override
    public Discussion startDiscussionCommunity(Long userStart, String title, String userList, String discussionList,String image) {
        Discussion discussion = new Discussion();
        discussion.setTypeDiscussion(TypeDiscussion.Community);
        discussion.setTitle(title);
        discussion.getUsers().add(iUserRepository.findById(userStart).get());
        if (userList != null && !userList.isEmpty()) {
            String[] userIdStrings = userList.split("_");

            for (String userIdString : userIdStrings) {
                Long userId = Long.parseLong(userIdString);
                if (iUserRepository.existsById(userId)) {
                    discussion.getUsers().add(iUserRepository.findById(userId).get());
                }
            }
        }

        String[] splitArray = discussionList.split("_");
        List<String> discussionListo = Arrays.asList(splitArray);

        for (String discussionx : discussionListo) {
            Discussion discussiono = new Discussion();
            discussiono.setTypeDiscussion(TypeDiscussion.CommunitySlave);
            discussiono.setTitle(discussionx);
            discussiono.setDateStart(LocalDateTime.now());
            discussiono.setArchived(false);
            discussion.getCommunity().add(discussiono);
           iDiscussionRepository.save(discussiono) ;
        }

        discussion.setPhoto(image);
        discussion.setDateStart(LocalDateTime.now());
        discussion.setArchived(false);
        return iDiscussionRepository.save(discussion) ;
    }

    @Override
    public Discussion modifyDiscussionGroup(Long discussion, Long userStart, String title, String userList, String image) {
        Discussion discussiono =iDiscussionRepository.findById(discussion).get();
        discussiono.setTitle(title);

        discussiono.getUsers().clear();
        discussiono.getUsers().add(iUserRepository.findById(userStart).get());

        if (userList != null && !userList.isEmpty()) {
            String[] userIdStrings = userList.split("_");

            for (String userIdString : userIdStrings) {
                Long userId = Long.parseLong(userIdString);
                if (iUserRepository.existsById(userId)) {
                    discussiono.getUsers().add(iUserRepository.findById(userId).get());
                }
            }
        }

        discussiono.setPhoto(image);

        return iDiscussionRepository.save(discussiono);
    }

    @Override
    public Discussion addDiscussionToCommunity(Long id, String discussionList) {
        Discussion discussion = iDiscussionRepository.findById(id).get();
        String[] splitArray = discussionList.split("|");
        List<String> discussionListo = Arrays.asList(splitArray);

        for (String discussionx : discussionListo) {
            Discussion discussiono = new Discussion();
            discussiono.setTypeDiscussion(TypeDiscussion.CommunitySlave);
            discussion.setTitle(discussionx);
            discussion.setDateStart(LocalDateTime.now());
            discussion.setArchived(false);
            discussion.getCommunity().add(discussiono);
            iDiscussionRepository.save(discussiono) ;
        }
        return iDiscussionRepository.save(discussion) ;
    }

    public List<Discussion> retrieveAllDiscussions(Long id){
        User user = iUserRepository.findById(id).get();
        return iDiscussionRepository.findByUsersContainingAndArchivedIsFalseAndTypeDiscussionIsNot(user,TypeDiscussion.Community);
    }

    public List<Discussion> retrieveAllCommunities(Long id){
        User user = iUserRepository.findById(id).get();
        return iDiscussionRepository.findByUsersContainingAndArchivedIsFalseAndTypeDiscussionIs(user,TypeDiscussion.Community);

    }

    @Override
    public Discussion renameDiscussion(Long id, String title) {
        Discussion discussion = iDiscussionRepository.findById(id).get();
        discussion.setTitle(title);
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
