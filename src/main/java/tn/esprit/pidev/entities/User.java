package tn.esprit.pidev.entities;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import tn.esprit.pidev.entities.evaluation.Score;
import tn.esprit.pidev.token.Token;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//@Data
@Builder
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nom;
    private String prenom;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
    private String photo;
    private Boolean isArchived = false;
    private Boolean isBanned = false;
    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Token> tokens;

    /********evaluation*******************/
    /*@OneToMany(mappedBy="user")
    @JsonIgnore
    private List<Answer> listAnswer;*/

    @OneToMany(mappedBy="user")
    @JsonIgnore
    private List<Score> listScore;
    /*************ressource*********************/
    @JsonIgnore
    @OneToMany(mappedBy = "auteur")
    private List<Commentaire> commentaires;
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

    public void setPassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        this.password = encoder.encode(password);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}