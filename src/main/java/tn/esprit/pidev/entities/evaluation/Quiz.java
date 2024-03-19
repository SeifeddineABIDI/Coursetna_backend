package tn.esprit.pidev.entities.evaluation;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Quiz implements Serializable {
    @Id
    @GeneratedValue
    private Long numQuiz;
    private String title;
    private String description;
    private int duree;
}
