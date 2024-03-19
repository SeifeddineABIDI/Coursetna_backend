package tn.esprit.pidev.entities.evaluation;

import jakarta.persistence.*;
import tn.esprit.pidev.entities.User;

import java.io.Serializable;
import java.util.Date;

public class Certificate implements Serializable {
    @Id
    @GeneratedValue
    private Long numCertif;

    @ManyToOne
    private User user;

    @ManyToOne
    private Quiz quiz;

    private String descriptionCertif;
    private Date dateIssued;
    private Date expirationDate;
}
