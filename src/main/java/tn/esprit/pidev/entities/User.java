package tn.esprit.pidev.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tn.esprit.pidev.entities.evaluation.Reponse;
import tn.esprit.pidev.entities.evaluation.Score;

import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private TypeUser role;
    private String photo;

    @OneToMany(mappedBy="user")
    @JsonIgnore
    private List<Reponse> listReponse;

    @OneToMany(mappedBy="user")
    @JsonIgnore
    private List<Score> listScore;

}
