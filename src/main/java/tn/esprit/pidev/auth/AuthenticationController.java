package tn.esprit.pidev.auth;


import com.sun.security.auth.UserPrincipal;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.pidev.config.LogoutService;
import tn.esprit.pidev.entities.CurrentUser;
import tn.esprit.pidev.entities.Role;
import tn.esprit.pidev.entities.User;
import tn.esprit.pidev.repository.IUserRepository;
import tn.esprit.pidev.services.IGestionUser;
import tn.esprit.pidev.user.ChangePasswordRequest;
import tn.esprit.pidev.user.UserService;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.security.Principal;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    @Autowired
    private  AuthenticationService service;
    @Autowired
    private IGestionUser userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private ResourceLoader resourceLoader;
    @Autowired
    private IUserRepository ur;
    @Autowired
    private LogoutService logoutService;
    private Boolean userExists;
    private String imagePath= "";

    private static final String IMAGE_DIRECTORY = "src/main/resources/static/images";


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
            @RequestParam("photo") MultipartFile imageFile
    ) {
        userExists = ur.existsByEmail(email);
        if(userExists == false){
        // Save the image and get the path
        imagePath = saveImage(imageFile);}

        // Create a RegisterRequest object
        RegisterRequest request = RegisterRequest.builder()
                .nom(nom)
                .prenom(prenom)
                .email(email)
                .password(password)
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

            // Generate a unique identifier
            String uniqueId = UUID.randomUUID().toString().replace("-", "");

            // Extract file extension
            String fileExtension = originalFileName.substring(originalFileName.lastIndexOf('.'));

            // Append unique identifier to the filename
            String modifiedFileName = uniqueId + "_" + originalFileName;

            // Get the path to save the image
            Path filePath = uploadPath.resolve(modifiedFileName);

            // Check if the file with the modified name already exists
            int count = 1;
            while (Files.exists(filePath)) {
                modifiedFileName = uniqueId + "_" + count + "_" + originalFileName;
                filePath = uploadPath.resolve(modifiedFileName);
                count++;
            }

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
    @PostMapping("/authenticateWithToken")
    public ResponseEntity<AuthenticationResponse> authenticateWithToken(
            @RequestBody Map<String,String> token
    ) {
        return ResponseEntity.ok(service.authenticateWithToken(token.get("token")));
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
    @GetMapping("/{userId}/image")
    public ResponseEntity<byte[]> getUserImage(@PathVariable Integer userId) {
        // Retrieve user by ID
        User user = userRepository.findById(userId);
        if (user == null || user.getPhoto() == null) {
            return ResponseEntity.notFound().build();
        }

        // Read image data from the file system using the path stored in the database
        Path imagePath = Paths.get(user.getPhoto());
        byte[] imageData;
        try {
            imageData = Files.readAllBytes(imagePath);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        // Set content type header based on image type (assuming JPEG for now)
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);

        // Return image data as response
        return ResponseEntity.ok()
                .headers(headers)
                .body(imageData);
    }

    @PatchMapping("/changepass/{userId}")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest request,@PathVariable Integer userId) {
        userService.changePassword(userId, request);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/mailcheck")
    public ResponseEntity<String> mailcheck(
            @RequestBody Map<String, String> requestBody
    ) {
        String email = requestBody.get("email");
        User user = userRepository.findUserBymail(email);
        System.out.println(email);
        System.out.println(user);
        if (user == null) {
            return ResponseEntity.ok("User doesn't exist!");
        } else {
            return ResponseEntity.ok("User exists!");
        }
    }
    @GetMapping("/current")
    public User getCurrentUser() {
        return CurrentUser.getUser();
    }
//    @PostMapping("/sign-out")
//    public void logout(
//            HttpServletRequest request,
//            HttpServletResponse response
//    ) throws IOException {
//        logoutService.logout(request, response);
//    }
    }

