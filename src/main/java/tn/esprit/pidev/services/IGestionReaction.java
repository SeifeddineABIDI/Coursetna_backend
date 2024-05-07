package tn.esprit.pidev.services;

import tn.esprit.pidev.entities.Reaction;

public interface IGestionReaction {

    public Reaction reacting (int user, Long message, String reaction);

}
