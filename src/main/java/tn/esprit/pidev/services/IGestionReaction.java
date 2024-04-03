package tn.esprit.pidev.services;

import tn.esprit.pidev.entities.Reaction;

public interface IGestionReaction {

    public Reaction addReaction (Long user, Long message, String reaction);

    public boolean deleteReaction (Long id);

}
