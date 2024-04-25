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
    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String photo = "";
    @Enumerated(EnumType.STRING)
    private TypeDiscussion typeDiscussion;
    @ManyToMany(cascade = CascadeType.DETACH)
    private List<User> users = new ArrayList<User>();
    @OneToMany
    private List<Discussion> community = new ArrayList<Discussion>();
    private boolean archived = false ;

    @Override
    public String toString() {
        return "Discussion{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", dateStart=" + dateStart +
                ", photo='" + photo + '\'' +
                ", typeDiscussion=" + typeDiscussion +
                ", users=" + users +
                ", archived=" + archived +
                '}';
    }
}
