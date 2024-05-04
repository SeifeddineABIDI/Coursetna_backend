package tn.esprit.pidev.entities;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import org.jetbrains.annotations.NotNull;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Vote implements Serializable {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long voteId;
    private VoteType voteType;
    @NotNull
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "postId", referencedColumnName = "postId")
    private Post post;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "userId", referencedColumnName = "id")
    private User user;
}
