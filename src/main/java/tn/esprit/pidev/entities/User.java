package tn.esprit.pidev.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import tn.esprit.pidev.entities.ressources.Badge;
import tn.esprit.pidev.entities.ressources.Commentaire;
import tn.esprit.pidev.entities.ressources.Notification;
import tn.esprit.pidev.entities.ressources.Ressource;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    @JsonIgnore
    @OneToMany(mappedBy = "auteur")
    private List<Commentaire> commentaires;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_badge",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "badge_id"))
    private Set<Badge> badges = new HashSet<>();

    public void addBadge(Badge badge) {
        badges.add(badge);
        badge.getUsers().add(this);
    }

    public void removeBadge(Badge badge) {
        badges.remove(badge);
        badge.getUsers().remove(this);
    }

}
