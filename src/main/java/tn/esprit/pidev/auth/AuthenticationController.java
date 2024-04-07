package tn.esprit.pidev.auth;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.pidev.entities.Role;
import tn.esprit.pidev.entities.User;
import tn.esprit.pidev.repository.IUserRepository;
import tn.esprit.pidev.services.IGestionUser;
import tn.esprit.pidev.user.ChangePasswordRequest;
import tn.esprit.pidev.user.UserService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.security.Principal;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    @Autowired
    private  AuthenticationService service;



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
            @RequestParam("photo") MultipartFile imageFile
    ) {
        // Save the image and get the path
        String imagePath = saveImage(imageFile);

        // Create a RegisterRequest object
        RegisterRequest request = RegisterRequest.builder()
                .nom(nom)
                .prenom(prenom)
                .email(email)
                .password(password)
                .role(role)
                .photo(imagePath)
                .build();

        // Call the register method in the service
        AuthenticationResponse response = service.register(request);

        // Return the response
        return ResponseEntity.ok(response);
    }

    private String saveImage(MultipartFile imageFile) {
        try {
            // Get the path to the resources/static directory
            String uploadDir = "src/main/resources/static/images";

            // Create the directory if it doesn't exist
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Get the original filename of the uploaded file
            String originalFileName = StringUtils.cleanPath(imageFile.getOriginalFilename());

            // Get the path to save the image
            Path filePath = uploadPath.resolve(originalFileName);

            // Save the image to the specified path
            Files.copy(imageFile.getInputStream(), filePath);

            // Return the path where the image is saved
            return filePath.toString();
        } catch (IOException ex) {
            throw new RuntimeException("Failed to save image", ex);
        }
    }

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

