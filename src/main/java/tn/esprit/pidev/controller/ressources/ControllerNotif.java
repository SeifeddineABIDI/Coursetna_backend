package tn.esprit.pidev.controller.ressources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.pidev.entities.ressources.Notification;
import tn.esprit.pidev.services.ressources.IGestionNotif;

import java.util.List;

@RestController
@RequestMapping("/notif")
public class ControllerNotif {
    @Autowired
    IGestionNotif notification;
    @GetMapping("/getall")
    public List<Notification> getAllNotif(){
        return  notification.getAll();
    }

}


