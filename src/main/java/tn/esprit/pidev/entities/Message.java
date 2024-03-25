package tn.esprit.pidev.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    @Id
    @GeneratedValue
    private Long id;
    private String message;
    private LocalDateTime dateSent;                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 ;
    private LocalDateTime dateModified;
    @ManyToOne
    private User user;
    @ManyToOne
    private Discussion discussion;
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
