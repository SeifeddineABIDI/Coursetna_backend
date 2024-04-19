package tn.esprit.pidev.entities.evaluation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

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

    @OneToMany(mappedBy = "quiz",cascade = CascadeType.ALL)
    private List<Question> listQuestion;

    @OneToMany(mappedBy = "quiz")
    @JsonIgnore
    private List<Score> listScore;


}
