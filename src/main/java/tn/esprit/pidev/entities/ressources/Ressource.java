package tn.esprit.pidev.entities.ressources;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import tn.esprit.pidev.entities.user.User;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
public class Ressource implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titre;
    private String description;
    private String typeFichier;
    private long tailleFichier;
    @ManyToOne
    @JoinColumn(name = "topicName")
    private Topic topic;
    @Enumerated(EnumType.STRING)
    private Categorie categorie;
    @Enumerated(EnumType.STRING)
    private Options options;
    private Date dateTlg;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User auteur;
    private int rating;
    private int numberOfRatings;
    private String filePath;
    @JsonIgnore
    @OneToMany(mappedBy = "ressource", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Notification> notifications;
    @JsonIgnore
    @OneToMany(mappedBy = "ressource", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<VersionRessource> versions;
    private boolean archived;




}
