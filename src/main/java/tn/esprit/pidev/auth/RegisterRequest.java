package tn.esprit.pidev.auth;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.pidev.entities.Role;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RegisterRequest {

    private String nom;
    private String prenom;
    private String email;
    private String password;
    private String photo;
}