package tn.esprit.pidev.entities.ressources;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;
import tn.esprit.pidev.entities.user.User;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
public class Commentaire implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCommentaire;

    @NotNull
    private String contenu;

    private Date datePublication;

    private int likes;

    private int dislikes;

    private String emotion;

    @ManyToMany
    @JoinTable(
            name = "utilisateur_commentaire",
            joinColumns = @JoinColumn(name = "commentaire_id"),
            inverseJoinColumns = @JoinColumn(name = "utilisateur_id"))
    private Set<User> utilisateurs = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "ressource_commentaire",
            joinColumns = @JoinColumn(name = "commentaire_id"),
            inverseJoinColumns = @JoinColumn(name = "ressource_id"))
    private Set<Ressource> ressources = new HashSet<>();


}
