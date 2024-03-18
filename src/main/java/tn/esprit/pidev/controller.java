package tn.esprit.pidev;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.pidev.entities.User;
import tn.esprit.pidev.services.IGestionUser;

import java.util.List;

@RestController
@RequestMapping("/user")
public class controller {
    @Autowired
    IGestionUser iGestionUser;
    public List<User> getAll(){return iGestionUser.getAll();}

}
