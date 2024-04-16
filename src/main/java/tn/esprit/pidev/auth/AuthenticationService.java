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
import tn.esprit.pidev.entities.CurrentUser;
import tn.esprit.pidev.entities.Role;
import tn.esprit.pidev.entities.User;
import tn.esprit.pidev.repository.IUserRepository;
import tn.esprit.pidev.services.EmailService;
import tn.esprit.pidev.token.Token;
import tn.esprit.pidev.token.TokenRepository;
import tn.esprit.pidev.token.TokenType;

import java.io.IOException;
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
    private  EmailService emailService;
    public AuthenticationResponse register(RegisterRequest request) {

         user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ADMIN)
                 .nom(request.getNom())
                 .prenom(request.getPrenom())
                 .photo(request.getPhoto())
                .isBanned(true)
                .isArchived(false)
                 .build();
        var savedUser = repository.save(user);
        jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(savedUser, jwtToken);


        emailService.sendConfirmationEmail("sayf.abidi1@gmail.com", user.getPrenom(), user.getNom(),user.getUsername(), String.format(CONFIRMATION_URL,jwtToken));


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
            CurrentUser.setUser(user);
            CurrentUser.getUser().getPrenom();
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
    public AuthenticationResponse authenticateWithToken(String token) {
        try {

            var user = tokenRepository.findUserByToken(token);

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
            CurrentUser.setUser(user);

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
            emailService.sendConfirmationEmail("sayf.abidi1@gmail.com", user.getPrenom(),user.getNom() ,user.getUsername(), String.format(CONFIRMATION_URL,jwtToken));

            return "Token expired a new token has been sent to your mail";
        }else if (savedToken.isPresent()) {
            User user = savedToken.get().getUser();
            user.setIsBanned(false);
            repository.save(user);
            tokenRepository.save(savedToken.get());
            return "<!DOCTYPE html>\n" +
                    "<html lang=\"en\">\n" +
                    "<head>\n" +
                    "    <meta charset=\"UTF-8\">\n" +
                    "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                    "    <title>Account Verified Successfully</title>\n" +
                    "    <style>\n" +
                    "        body {\n" +
                    "            font-family: Arial, sans-serif;\n" +
                    "            background-color: #f4f4f4;\n" +
                    "            margin: 0;\n" +
                    "            padding: 0;\n" +
                    "            display: flex;\n" +
                    "            justify-content: center;\n" +
                    "            align-items: center;\n" +
                    "            height: 100vh;\n" +
                    "        }\n" +
                    "        .container {\n" +
                    "            text-align: center;\n" +
                    "            background-color: #fff;\n" +
                    "            border-radius: 8px;\n" +
                    "            padding: 40px;\n" +
                    "            box-shadow: 0 0 20px rgba(0, 0, 0, 0.1);\n" +
                    "        }\n" +
                    "        h1 {\n" +
                    "            color: #2196F3;\n" +
                    "        }\n" +
                    "        .button {\n" +
                    "            display: inline-block;\n" +
                    "            padding: 10px 20px;\n" +
                    "            background-color: #2196F3;\n" +
                    "            color: #fff;\n" +
                    "            text-decoration: none;\n" +
                    "            border-radius: 5px;\n" +
                    "            transition: background-color 0.3s ease;\n" +
                    "        }\n" +
                    "        .button:hover {\n" +
                    "            background-color: #1976D2;\n" +
                    "        }\n" +
                    "    </style>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "    <div class=\"container\">\n" +
                    "        <h1>Your account has been verified successfully!</h1>\n" +
                    "        <p>You can now sign in to your account.</p>\n" +
                    "        <a href=\"http://localhost:4200/sign-in\" class=\"button\">Sign In</a>\n" +
                    "    </div>\n" +
                    "</body>\n" +
                    "</html>\n";
        }
        else{
            return "err";
        }
    }
}