package tn.esprit.pidev.entities.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tn.esprit.pidev.entities.evaluation.Answer;
import tn.esprit.pidev.entities.evaluation.Score;
import tn.esprit.pidev.entities.reclamation.Reclamtion;
import tn.esprit.pidev.entities.ressources.Notification;
import tn.esprit.pidev.entities.ressources.Ressource;

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
    private Integer id;
    private String nom;
    private String prenom;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private TypeUser role;
    private String photo;
    private Boolean isArchived = false;
    private Boolean isBanned = false;

    /********evaluation*******************/
    @OneToMany(mappedBy="user")
    @JsonIgnore
    private List<Answer> listAnswer;

    @OneToMany(mappedBy="user")
    @JsonIgnore
    private List<Score> listScore;
    /*************ressource*********************/
    @JsonIgnore
    @OneToMany(mappedBy = "auteur")
    private List<Ressource> ressourcesPubliees;
    @JsonIgnore
    @OneToMany(mappedBy = "destinataire")
    private List<Notification> notifications;
    /*************reclamation*********************/
    @JsonIgnore
    @OneToMany(mappedBy = "user") // user 1 ------ * reclamtion
    private List<Reclamtion> reclamtionList;
    /******************************************/

}
