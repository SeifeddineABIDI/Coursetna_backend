package tn.esprit.pidev.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tn.esprit.pidev.entities.User;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Reclamtion implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idrec;
    private String titre;
    private String description;
    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    // Initialize status to non_traite by default

    @Enumerated(EnumType.STRING)
    private TypeStatus status = TypeStatus.non_traite;


    @ManyToOne
    private User user;

    @OneToMany(mappedBy ="reclamtion" , cascade = CascadeType.ALL)
    private List<Reponse> responses;
    @JsonIgnore
    @ManyToOne
    private Topic topic;

    // This method is called before the entity is persisted for the first time
    @PrePersist
    public void prePersist() {
        this.creationDate = LocalDateTime.now();
    }
}
