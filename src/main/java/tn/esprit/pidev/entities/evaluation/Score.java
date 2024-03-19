package tn.esprit.pidev.entities.evaluation;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tn.esprit.pidev.entities.User;

import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Score implements Serializable {
    @Id
    @GeneratedValue
    private Long numScore;
    private double score;
    private Date date;

    @ManyToOne
    private User user;
    @ManyToOne
    private Quiz quiz;

}
