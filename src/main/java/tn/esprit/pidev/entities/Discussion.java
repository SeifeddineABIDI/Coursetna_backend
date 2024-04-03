package tn.esprit.pidev.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Discussion implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private LocalDateTime dateStart;
    @Enumerated(EnumType.STRING)
    private TypeDiscussion typeDiscussion;
    @ManyToMany(cascade = CascadeType.DETACH)
    private List<User> users = new ArrayList<User>();
    @ManyToMany(cascade = CascadeType.DETACH)
    private List<User> acceptedUsers = new ArrayList<User>();
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "discussion")
    private List<Message> messages = new ArrayList<Message>();
    private boolean archived = false ;

    @Override
    public String toString() {
        return "Discussion{" +
                "id=" + id +
                ", title=" + title +
                ", dateStart=" + dateStart +
                ", typeDiscussion=" + typeDiscussion +
                ", users=" + users +
                ", messages=" + messages +
                ", archived=" + archived +
                '}';
    }
}
