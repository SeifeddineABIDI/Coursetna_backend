package tn.esprit.pidev.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.pidev.entities.User;
import tn.esprit.pidev.services.IGestionUser;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    IGestionUser iGestionUser;

    @GetMapping("/getAll")
    public List<User> getAll(){return iGestionUser.getAll();}

        @PostMapping("/addUser")
    public User add(@RequestBody User user) {
        return iGestionUser.add(user);
    }

}

