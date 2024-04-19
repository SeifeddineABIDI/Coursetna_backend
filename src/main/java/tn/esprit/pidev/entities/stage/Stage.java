package tn.esprit.pidev.entities.stage;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor



public class Stage {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)

    private int offreid ;
    private String title;
    private String description;
    private String location;
    private String company;
    private String duration;
    private Date publicationDate;






}
