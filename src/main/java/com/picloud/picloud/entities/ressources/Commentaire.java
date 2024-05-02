package com.picloud.picloud.entities.ressources;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.picloud.picloud.entities.User;
import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;

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

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User auteur;

    @ManyToOne
    @JoinColumn(name = "ressource_id")
    private Ressource ressource;


}
