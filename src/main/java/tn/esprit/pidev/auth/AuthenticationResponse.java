package tn.esprit.pidev.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tn.esprit.pidev.entities.User;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
    private User user;

    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("refresh_token")
    private String refreshToken;
    private String error;

}
