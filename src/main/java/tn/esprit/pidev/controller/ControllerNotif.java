package tn.esprit.pidev.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.pidev.entities.Notification;
import tn.esprit.pidev.services.IGestionNotif;

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


