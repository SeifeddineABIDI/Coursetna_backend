package tn.esprit.pidev.services;

import tn.esprit.pidev.entities.User;

import java.util.List;

public interface IGestionUser {
    List<User> findAll();
    List<User> findAllActive();
    User add(User user);
    User update(User user);
    User findById (Long id);
    void delete(Long id);

}
