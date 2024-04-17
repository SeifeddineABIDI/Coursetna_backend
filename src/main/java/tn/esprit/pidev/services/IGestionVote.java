package tn.esprit.pidev.services;

import tn.esprit.pidev.dto.VoteDto;

public interface IGestionVote {
    public void vote(VoteDto voteDto, String userEmail);
}
