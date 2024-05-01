package tn.esprit.pidev.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import tn.esprit.pidev.entities.Discussion;
import tn.esprit.pidev.entities.Message;
import tn.esprit.pidev.entities.TypeDiscussion;
import tn.esprit.pidev.entities.User;
import tn.esprit.pidev.repository.IDiscussionRepository;
import tn.esprit.pidev.repository.IUserRepository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class GestionDiscussionImpl implements IGestionDiscussion {
    @Autowired
    IDiscussionRepository iDiscussionRepository;
    @Autowired
    IUserRepository iUserRepository;
    @Override
    public Discussion startDiscussionDuo(Long userStart, Long userEnd)
    {
        List<Discussion> d = iDiscussionRepository.findByTwoUsersAndTypeDiscussion(iUserRepository.findById(userStart).get(),iUserRepository.findById(userEnd).get(),TypeDiscussion.Duo);
                if (!d.isEmpty()) {
                    d.get(0).setArchived(false);
                    d.get(0).getUsers().clear();
                   return iDiscussionRepository.save(d.get(0));
                } else {

                    Discussion discussion = new Discussion();
                    discussion.setTypeDiscussion(TypeDiscussion.Duo);
                    User userEndo = iUserRepository.findById(userEnd).get();
                    discussion.getUsers().add(iUserRepository.findById(userStart).get());
                    discussion.getAdmins().add(iUserRepository.findById(userStart).get());
                    discussion.getUsers().add(userEndo);
                    discussion.getAdmins().add(userEndo);
                    discussion.setTitle(userEndo.getPrenom() + " " + userEndo.getNom());
                    discussion.setDateStart(LocalDateTime.now());
                    discussion.setArchived(false);
                    return iDiscussionRepository.save(discussion);
                }
    }

    @Override
    public Discussion startDiscussionGroup(Long userStart, String title, String userList, String image) {
        Discussion discussion = new Discussion();
        discussion.setTypeDiscussion(TypeDiscussion.Group);
        discussion.setTitle(title);

        discussion.getUsers().add(iUserRepository.findById(userStart).get());
        discussion.getAdmins().add(iUserRepository.findById(userStart).get());

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
        discussion.getAdmins().add(iUserRepository.findById(userStart).get());

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
            discussiono.setPhoto(" ");
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
    public ResponseEntity<String> modifyDiscussionGroup(Long discussion, Long userStart, String title, String userList, Long admin, String image) {
        Discussion discussiono =iDiscussionRepository.findById(discussion).get();
        User admino = iUserRepository.findById(admin).get();

        if (discussiono.getAdmins().contains(admino)) {
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


            iDiscussionRepository.save(discussiono);
            return ResponseEntity.ok("Group discussion modified successfully.");
        } else {
            return new ResponseEntity<>("You are not an admin to be able to perform this operation!", HttpStatus.NOT_FOUND);

        }
    }


    @Override
    public ResponseEntity<String> modifyDiscussionCommunity(Long discussion, Long userStart, String title, String userList, String discussionList, Long admin, String image) {
        Discussion discussiono = iDiscussionRepository.findById(discussion).get();
        User admino = iUserRepository.findById(admin).get();

        if (discussiono.getAdmins().contains(admino)) {
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

            String[] splitArray = discussionList.split("_");
            List<String> discussionListoo = Arrays.asList(splitArray);
            List<String> discussionListo = new ArrayList<>(discussionListoo);


            for (Discussion discussionx : discussiono.getCommunity()) {
                if (!discussionx.isArchived()) {
                    if (!discussionListo.isEmpty()) {
                        discussionx.setTitle(discussionListo.get(0));
                        System.out.println(discussionListo.get(0));
                        discussionListo.remove(0);

                    } else {
                        discussionx.setArchived(true);
                    }

                }


            }

            while (discussionListo.stream().count() > 0) {
                Discussion discussionoo = new Discussion();
                discussionoo.setTypeDiscussion(TypeDiscussion.CommunitySlave);
                discussionoo.setTitle(discussionListo.get(0));
                discussionoo.setDateStart(LocalDateTime.now());
                discussionoo.setArchived(false);
                discussionoo.setPhoto(" ");
                discussionListo.remove(0);
                discussiono.getCommunity().add(discussionoo);
                iDiscussionRepository.save(discussionoo);
            }

            iDiscussionRepository.save(discussiono);
            return ResponseEntity.ok("Community discussion modified successfully.");

        } else {
            return new ResponseEntity<>("You are not an admin to be able to perform this operation!", HttpStatus.NOT_FOUND);

        }
    }

    public ResponseEntity<String> addAdminsToDiscussion(Long discussion, Long admin, String userList) {

        Discussion discussiono =iDiscussionRepository.findById(discussion).get();
        User usero = iUserRepository.findById(admin).get() ;
        if (discussiono.getAdmins().contains(usero))
        {
            discussiono.getAdmins().clear();
            discussiono.getAdmins().add(usero);

            if (userList != null && !userList.isEmpty()) {
                String[] userIdStrings = userList.split("_");

                for (String userIdString : userIdStrings) {
                    Long userId = Long.parseLong(userIdString);
                    if (iUserRepository.existsById(userId)) {
                        discussiono.getAdmins().add(iUserRepository.findById(userId).get());
                    }
                }

            }
            iDiscussionRepository.save(discussiono) ;

            return ResponseEntity.ok("Admins added successfully.");
        } else {
            return new ResponseEntity<>("You are not an admin to be able to perform this operation!", HttpStatus.NOT_FOUND);
        }

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
    public Discussion leaveDiscussion(Long user, Long discussion) {
        Discussion discussiono = iDiscussionRepository.findById(discussion).get() ;
        User usero = iUserRepository.findById(user).get() ;
        discussiono.getUsers().remove(usero);
        return iDiscussionRepository.save(discussiono) ;
    }
    @Override
    public ResponseEntity<String> deleteDiscussion(Long user, Long discussion) {
        Discussion discussiono = iDiscussionRepository.findById(discussion).get() ;
        User usero = iUserRepository.findById(user).get() ;
        if (discussiono.getAdmins().contains(usero))
        {
            discussiono.setArchived(true);
            iDiscussionRepository.save(discussiono) ;
            return new ResponseEntity<>("Discussion deleted successfully.",HttpStatus.OK);
        } else {
            return new ResponseEntity<>("You are not an admin to be able to perform this operation!", HttpStatus.NOT_FOUND);
        }


    }
}
