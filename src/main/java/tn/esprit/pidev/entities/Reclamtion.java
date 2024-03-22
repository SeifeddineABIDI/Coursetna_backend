package tn.esprit.pidev.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Reclamtion implements Serializable {
    @Id
    @GeneratedValue
    private long idrec;
    private String description;
    private LocalDate creationDate;

    @Enumerated(EnumType.STRING)
    private TypeStatus status;

    @ManyToOne // Reclamtion * ------ 1 User
    private User user;

}
