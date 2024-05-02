package tn.esprit.pidev.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.pidev.entities.user.User;
import tn.esprit.pidev.services.user.IGestionUser;

import java.util.List;

@RestController
@RequestMapping("/user")
public class controller {
    @Autowired
    IGestionUser iGestionUser;

    @GetMapping("/getAll")
    public List<User> findAll(){return iGestionUser.findAll();}

    @PostMapping("/addUser")
    public User add(@RequestBody User user) {
        return iGestionUser.add(user);
    }

}
