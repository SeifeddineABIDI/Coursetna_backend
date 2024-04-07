package tn.esprit.pidev.auth;

import lombok.*;
import tn.esprit.pidev.entities.Role;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RegisterRequest {

    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private Role role;
}