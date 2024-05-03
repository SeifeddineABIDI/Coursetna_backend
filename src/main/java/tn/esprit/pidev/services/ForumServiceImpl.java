package tn.esprit.pidev.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.pidev.entities.Forum;
import tn.esprit.pidev.repository.ForumRepository;

@Service
public class ForumServiceImpl implements ForumService {

    private final ForumRepository forumRepository;

    @Autowired
    public ForumServiceImpl(ForumRepository forumRepository) {
        this.forumRepository = forumRepository;
    }

    @Override
    public Forum saveForum(Forum forum) {
        return forumRepository.save(forum);
    }

    // Implémentez d'autres méthodes selon vos besoins
}
