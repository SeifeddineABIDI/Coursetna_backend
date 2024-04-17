package com.picloud.picloud.services;

import com.picloud.picloud.entities.User;
import com.picloud.picloud.entities.ressources.Ressource;

import java.util.List;

public interface IGestionUser {
    List<User> getAll();
    User add(User user);
    User update(User user);
    User getById (Long id);


}
