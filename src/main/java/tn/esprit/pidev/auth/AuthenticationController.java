package tn.esprit.pidev.auth;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.pidev.entities.Role;
import tn.esprit.pidev.entities.User;
import tn.esprit.pidev.repository.IUserRepository;
import tn.esprit.pidev.services.IGestionUser;
import tn.esprit.pidev.user.ChangePasswordRequest;
import tn.esprit.pidev.user.UserService;

import java.io.IOException;
import java.security.Principal;
import java.security.Principal;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService service;



//    @PostMapping("/register")
//    public ResponseEntity<AuthenticationResponse> register(
//            @RequestBody RegisterRequest request
//    ) {
//        return ResponseEntity.ok(service.register(request));
//    }
@PostMapping("/register")
public ResponseEntity<AuthenticationResponse> register(
        @RequestParam("nom") String nom,
        @RequestParam("prenom") String prenom,
        @RequestParam("email") String email,
        @RequestParam("password") String password,
        @RequestParam("role") Role role,
        @RequestParam("imageFile") MultipartFile imageFile
) {
    RegisterRequest request = RegisterRequest.builder()
            .firstname(nom)
            .lastname(prenom)
            .email(email)
            .password(password)
            .role(role)
            .photo(imageFile)
            .build();
    return ResponseEntity.ok(service.register(request, imageFile));}

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }

    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        service.refreshToken(request, response);
    }

    @GetMapping("/confirm")
    public ResponseEntity<String> confirm(
            @RequestParam("token") String token
    ) {
        return ResponseEntity.ok(service.confirmToken(token));
    }
}

