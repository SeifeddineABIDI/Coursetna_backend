package tn.esprit.pidev.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentsDto {
    private Long id;
    private Long postId;
    private String createdDate;
    @NotBlank
    private String text;
    private String email;
    private String nom;
    private String prenom;
    private int userId;


}