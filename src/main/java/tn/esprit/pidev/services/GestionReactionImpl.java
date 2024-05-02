package tn.esprit.pidev.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.pidev.entities.Discussion;
import tn.esprit.pidev.entities.Message;
import tn.esprit.pidev.entities.Reaction;
import tn.esprit.pidev.entities.User;
import tn.esprit.pidev.repository.IMessageRepository;
import tn.esprit.pidev.repository.IReactionRepository;
import tn.esprit.pidev.repository.IUserRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class GestionReactionImpl implements IGestionReaction {
    @Autowired
    IMessageRepository iMessageRepository;
    @Autowired
    IUserRepository iUserRepository;
    @Autowired
    IReactionRepository iReactionRepository;
    @Override


    public Reaction reacting(int userId, Long message, String reaction) {
        User user = iUserRepository.findById(userId).get();
        Message messageo = iMessageRepository.findById(message).get();

        Optional<Reaction> existingReaction = iReactionRepository.findFirstByUserAndReactionAndMessage(user, reaction, messageo);
        if (existingReaction.isPresent()) {
            existingReaction.get().setArchived(!existingReaction.get().isArchived());
            return iReactionRepository.save(existingReaction.get());
        } else {
            Reaction reactiono = new Reaction();
            reactiono.setReaction(reaction);
            reactiono.setDateReact(LocalDateTime.now());
            reactiono.setArchived(false);
            reactiono.setUser(user);
            reactiono.setMessage(messageo);
            messageo.getReactions().add(reactiono);
            iMessageRepository.save(messageo);
            return iReactionRepository.save(reactiono);
        }
    }
}
