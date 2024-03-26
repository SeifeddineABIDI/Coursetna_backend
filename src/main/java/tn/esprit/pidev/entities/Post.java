package tn.esprit.pidev.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Post implements Serializable {
    @Id
    @GeneratedValue
    private Long postId ;

    private String title;
    private String content;
    private String imageName;
    private Date addedDate;

    @ManyToOne
    private Category category;

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "post",cascade = CascadeType.ALL)
    private Set<Comment> comments=new HashSet<>();

    @ManyToMany(cascade = {
            CascadeType.MERGE, CascadeType.PERSIST,
            CascadeType.REFRESH, CascadeType.DETACH})
    @JoinTable(
            name = "post_like",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> positiveVotes;

    @ManyToMany(cascade = {
            CascadeType.MERGE, CascadeType.PERSIST,
            CascadeType.REFRESH, CascadeType.DETACH})
    @JoinTable(
            name = "post_dislike",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> negativeVotes;

    public void addPositiveVote(User user) {
        if (positiveVotes == null) {
            positiveVotes = new HashSet<>();
        }
        positiveVotes.add(user);
    }

    public void removePositiveVote(User user) {
        if (positiveVotes != null) {
            positiveVotes.remove(user);
        }
    }

    public void addNegativeVote(User user) {
        if (negativeVotes == null) {
            negativeVotes = new HashSet<>();
        }
        negativeVotes.add(user);
    }

    public void removeNegativeVote(User user) {
        if (negativeVotes != null) {
            negativeVotes.remove(user);
        }
    }

    public Integer getRating() {
        int positiveCount = positiveVotes != null ? positiveVotes.size() : 0;
        int negativeCount = negativeVotes != null ? negativeVotes.size() : 0;
        return positiveCount - negativeCount;
    }
}

