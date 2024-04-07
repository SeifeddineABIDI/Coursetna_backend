package tn.esprit.pidev.auth;


import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.pidev.config.JwtService;
import tn.esprit.pidev.entities.User;
import tn.esprit.pidev.repository.IUserRepository;
import tn.esprit.pidev.services.EmailService;
import tn.esprit.pidev.services.ImageService;
import tn.esprit.pidev.token.Token;
import tn.esprit.pidev.token.TokenRepository;
import tn.esprit.pidev.token.TokenType;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final String CONFIRMATION_URL="http://localhost:9000/pidev/api/v1/auth/confirm?token=%s";
    private final IUserRepository repository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private User user;
    private String jwtToken;
    private final AuthenticationManager authenticationManager;
    @Autowired
    private ImageService imageService;
    @Autowired
    private  EmailService emailService;
    public AuthenticationResponse register(RegisterRequest request,  MultipartFile imageFile) {
        String imagePath = null;
        if (imageFile != null && !imageFile.isEmpty()) {
            // Save the uploaded image
            String imageName = StringUtils.cleanPath(imageFile.getOriginalFilename());
            imagePath = imageService.saveImage(imageFile, imageName);
        }
         user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .isBanned(true)
                .isArchived(false)
                 .photo(imagePath) // Set image path if provided

                 .build();
        var savedUser = repository.save(user);
        jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(savedUser, jwtToken);


        emailService.sendConfirmationEmail("sayf.abidi1@gmail.com", user.getUsername(), String.format(CONFIRMATION_URL,jwtToken));


        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
            var user = repository.findByEmail(request.getEmail())
                    .orElseThrow();
            if (user.getIsBanned()== true)
            {
                return AuthenticationResponse.builder()
                        .user(null)
                        .accessToken(null)
                        .refreshToken(null)
                        .error("user not activated")
                        .build();
            }
            var jwtToken = jwtService.generateToken(user);
            var refreshToken = jwtService.generateRefreshToken(user);
            revokeAllUserTokens(user);
            saveUserToken(user, jwtToken);

            return AuthenticationResponse.builder()
                    .user(user)
                    .accessToken(jwtToken)
                    .refreshToken(refreshToken)
                    .error(null)
                    .build();
        } catch (AuthenticationException ex) {
            // Handle authentication failure
            return AuthenticationResponse.builder()
                    .user(null)
                    .accessToken(null)
                    .refreshToken(null)
                    .error("Invalid username or password")
                    .build();
        } catch (Exception ex) {
            // Handle other exceptions
            return AuthenticationResponse.builder()
                    .user(null)
                    .accessToken(null)
                    .refreshToken(null)
                    .error("Internal server error")
                    .build();
        }
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail != null) {
            var user = this.repository.findByEmail(userEmail)
                    .orElseThrow();
            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                var authResponse = HttpResponse .builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }
    public String confirmToken(String token) {
        Optional<Token> savedToken = tokenRepository.findByToken(token);
        if (savedToken.get().isExpired()) {
            String generatedToken = UUID.randomUUID().toString();
            Token newToken = Token.builder()
                    .token(generatedToken)
                    .user(savedToken.get().getUser())
                    .build();
            emailService.sendConfirmationEmail("sayf.abidi1@gmail.com", user.getUsername(), String.format(CONFIRMATION_URL,jwtToken));

            return "Token expired a new token has been sent to your mail";
        }else if (savedToken.isPresent()) {
            User user = savedToken.get().getUser();
            user.setIsBanned(false);
            repository.save(user);
            tokenRepository.save(savedToken.get());
            return "account activated";
        }
        else{
            return "err";
        }
    }
}