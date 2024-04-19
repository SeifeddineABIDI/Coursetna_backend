package tn.esprit.pidev.entities.reclamation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tn.esprit.pidev.entities.user.User;

import java.io.Serializable;
import java.time.LocalDate;
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
    private String description;
    private LocalDate creationDate;

    @Enumerated(EnumType.STRING)
    private TypeStatus status;

    @ManyToOne
    private User user;
    @JsonIgnore
    @OneToMany(mappedBy ="reclamtion" , cascade = CascadeType.ALL)
    private List<Reponse> responses;

}
