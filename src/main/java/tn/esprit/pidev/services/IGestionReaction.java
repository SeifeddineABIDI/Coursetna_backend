package tn.esprit.pidev.services;

import tn.esprit.pidev.entities.Reaction;

public interface IGestionReaction {

    public Reaction reacting (Long user, Long message, String reaction);

}
