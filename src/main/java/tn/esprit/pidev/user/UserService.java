package tn.esprit.pidev.user;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import tn.esprit.pidev.entities.User;
import tn.esprit.pidev.repository.IUserRepository;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private  PasswordEncoder passwordEncoder;
    @Autowired
    private final IUserRepository repository;
//    public void changePassword(ChangePasswordRequest request) {
//        User connectedUser = repository.findUserByEmail(request.getEmail());
//        System.out.println("******************************************");
//        if (connectedUser == null) {
//            throw new IllegalStateException("User not authenticated");
//        }
//
//
//        // check if the current password is correct
//        if (!passwordEncoder.matches(request.getCurrentPassword(), connectedUser.getPassword())) {
//            throw new IllegalStateException("Wrong password");
//        }
//        // check if the two new passwords are the same
//
//
//        // update the password
//        connectedUser.setPassword(passwordEncoder.encode(request.getNewPassword()));
//
//        // save the new password
//        repository.save(connectedUser);
//    }
public void changePassword(Integer userId, ChangePasswordRequest request) {
    User user = repository.findById(userId)
            .orElseThrow(() -> new NotFoundException("User not found"));

    // Validate old password
    if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
        throw new IllegalArgumentException("Incorrect old password");
    }

    // Set new password
    user.setPassword(request.getNewPassword());
    // Save updated user
    repository.save(user);
}
}