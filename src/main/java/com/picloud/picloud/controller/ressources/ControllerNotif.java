package com.picloud.picloud.controller.ressources;

import com.picloud.picloud.entities.ressources.Notification;
import com.picloud.picloud.services.ressources.IGestionNotif;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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


