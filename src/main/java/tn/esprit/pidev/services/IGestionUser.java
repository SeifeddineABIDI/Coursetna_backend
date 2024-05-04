package tn.esprit.pidev.services;

import tn.esprit.pidev.entities.User;

import java.util.List;

public interface IGestionUser {
    List<User> getAll();
    User add(User user);
    User update(User user);
    User getById (Long id);


}
