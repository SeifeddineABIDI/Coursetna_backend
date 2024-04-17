package com.picloud.picloud.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.picloud.picloud.entities.ressources.Commentaire;
import com.picloud.picloud.entities.ressources.Notification;
import com.picloud.picloud.entities.ressources.Ressource;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
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
    @JsonIgnore
    @OneToMany(mappedBy = "auteur")
    private List<Ressource> ressourcesPubliees;
    @JsonIgnore
    @OneToMany(mappedBy = "destinataire")
    private List<Notification> notifications;
}
