package tn.esprit.pidev.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    @Id
    @GeneratedValue
    private Long id;
    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String message;
    private LocalDateTime dateSent;                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 ;
    private LocalDateTime dateModified;
    @ManyToOne
    private User user;
    @ManyToOne
    private Discussion discussion;
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "message")
    private List<Reaction> reactions = new ArrayList<Reaction>();
    @ManyToOne
    private Message reply;
    private boolean pinned;
    private boolean archived;

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", message='" + message + '\'' +
                ", dateSent=" + dateSent +
                ", dateModified=" + dateModified +
                ", user=" + user +
                ", discussion=" + discussion +
                ", archived=" + archived +
                '}';
    }
}
