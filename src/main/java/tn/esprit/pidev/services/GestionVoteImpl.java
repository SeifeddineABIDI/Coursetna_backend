package tn.esprit.pidev.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.pidev.dto.VoteDto;
import tn.esprit.pidev.entities.Post;
import tn.esprit.pidev.entities.User;
import tn.esprit.pidev.entities.Vote;
import tn.esprit.pidev.exceptions.PostNotFoundException;
import tn.esprit.pidev.exceptions.SpringSubforumException;
import tn.esprit.pidev.exceptions.UserNotFoundException;
import tn.esprit.pidev.repository.IPostRepository;
import tn.esprit.pidev.repository.IUserRepository;
import tn.esprit.pidev.repository.IVoteRepository;
import tn.esprit.pidev.services.IGestionUser;
import tn.esprit.pidev.services.IGestionVote;

import java.util.Optional;

import static tn.esprit.pidev.entities.VoteType.UPVOTE;

@Service
public class GestionVoteImpl implements IGestionVote {

    @Autowired
    private IPostRepository postRepository;

    @Autowired
    private IVoteRepository voteRepository;
    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IGestionUser gestionUser;

    @Override
    public void vote(VoteDto voteDto, String userEmail) {
        Post post = postRepository.findById(voteDto.getPostId())
                .orElseThrow(() -> new PostNotFoundException("Post Not Found with ID - " + voteDto.getPostId()));


        User user = userRepository.findByEmail(voteDto.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + voteDto.getEmail()));

        Optional<Vote> voteByPostAndUser = voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post, user);
        if (voteByPostAndUser.isPresent() &&
                voteByPostAndUser.get().getVoteType().equals(voteDto.getVoteType())) {
            throw new SpringSubforumException("You have already "
                    + voteDto.getVoteType() + "'d for this post");
        }
        if (UPVOTE.equals(voteDto.getVoteType())) {
            post.setVoteCount(post.getVoteCount() + 1);
        } else {
            post.setVoteCount(post.getVoteCount() - 1);
        }
        voteRepository.save(mapToVote(voteDto, post, user));
        postRepository.save(post);
    }

    private Vote mapToVote(VoteDto voteDto, Post post, User user) {
        return Vote.builder()
                .voteType(voteDto.getVoteType())
                .post(post)
                .user(user)
                .build();
    }
}
