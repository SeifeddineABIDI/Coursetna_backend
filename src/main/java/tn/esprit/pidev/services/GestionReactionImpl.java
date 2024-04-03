package tn.esprit.pidev.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.pidev.entities.Discussion;
import tn.esprit.pidev.entities.Message;
import tn.esprit.pidev.entities.Reaction;
import tn.esprit.pidev.repository.IMessageRepository;
import tn.esprit.pidev.repository.IReactionRepository;
import tn.esprit.pidev.repository.IUserRepository;

import java.time.LocalDateTime;

@Service
public class GestionReactionImpl implements IGestionReaction {
    @Autowired
    IMessageRepository iMessageRepository;
    @Autowired
    IUserRepository iUserRepository;
    @Autowired
    IReactionRepository iReactionRepository;
    @Override
    public Reaction addReaction (Long user, Long message, String reaction){
    Reaction reactiono = new Reaction();
        reactiono.setReaction(reaction);
        reactiono.setDateReact(LocalDateTime.now());
        reactiono.setArchived(false);
        reactiono.setUser(iUserRepository.findById(user).get());
        Message messageo = iMessageRepository.findById(message).get();
        messageo.getReactions().add(reactiono);

        iMessageRepository.save(messageo);
        return iReactionRepository.save(reactiono);
    }

    public boolean deleteReaction(Long id) {
        Reaction reaction = iReactionRepository.findById(id).get();
        reaction.setArchived(true);
        try {
            iReactionRepository.save(reaction);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
