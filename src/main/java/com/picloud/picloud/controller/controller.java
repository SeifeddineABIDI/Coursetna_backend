package com.picloud.picloud.controller;


import com.picloud.picloud.entities.User;
import com.picloud.picloud.services.IGestionUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



import java.util.List;

    @RestController
    @RequestMapping("/user")
    public class controller {
    @Autowired
    IGestionUser iGestionUser;
    public List<User> getAll(){return iGestionUser.getAll();}

    }


