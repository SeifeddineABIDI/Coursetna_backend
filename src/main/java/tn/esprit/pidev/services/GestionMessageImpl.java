package tn.esprit.pidev.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.pidev.entities.Discussion;
import tn.esprit.pidev.entities.Message;
import tn.esprit.pidev.repository.IDiscussionRepository;
import tn.esprit.pidev.repository.IMessageRepository;
import tn.esprit.pidev.repository.IUserRepository;

import java.time.LocalDateTime;

@Service
public class GestionMessageImpl implements IGestionMessage {
    @Autowired
    IMessageRepository iMessageRepository;
    @Autowired
    IUserRepository iUserRepository;
    @Autowired
    IDiscussionRepository iDiscussionRepository;
    @Override
    public Message sendMessage(Long sender, Long discussion, String message) {
        Message messageo = new Message();
        messageo.setMessage(message);
        messageo.setDateSent(LocalDateTime.now());
        messageo.setArchived(false);
        messageo.setUser(iUserRepository.findById(sender).get());

        Discussion discussiono = iDiscussionRepository.findById(discussion).get();
        messageo.setDiscussion(discussiono);
        discussiono.getMessages().add(messageo);

        iDiscussionRepository.save(discussiono);
        return iMessageRepository.save(messageo);
    }

    public Message modifyMessage(Long id, String message) {
        Message messageo = iMessageRepository.findById(id).get();
        messageo.setDateModified(LocalDateTime.now());
        messageo.setMessage(message);
        return iMessageRepository.save(messageo);
    }

    public boolean deleteMessage(Long id) {
        Message message = iMessageRepository.findById(id).get();
        message.setArchived(true);
        try {
            iMessageRepository.save(message);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
