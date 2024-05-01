package tn.esprit.pidev.demo;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.pidev.user.ChangePasswordRequest;
import tn.esprit.pidev.user.UserService;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/demo-controller")
@Hidden
public class DemoController {
    @Autowired
    UserService service;
    @GetMapping
    public ResponseEntity<String> sayHello() {
        return ResponseEntity.ok("Hello from secured endpoint");
    }

    @GetMapping("/ping")
    public String test() {
        try {
            return "Welcome";
        } catch (Exception e) {
            return "ghalet";
        }
    }
//    @PatchMapping
//    public ResponseEntity<?> changePassword(
//            @RequestBody ChangePasswordRequest request,
//            Principal connectedUser
//    ) {
//        service.changePassword(request, connectedUser);
//        return ResponseEntity.ok().build();
//    }
}


