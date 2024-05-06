package com.example.picloud.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
