package tn.esprit.pidev.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tn.esprit.pidev.entities.VoteType;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoteDto {
    private VoteType voteType;
    private Long postId;
    private String email;
}
