package tn.esprit.pidev.services;

import tn.esprit.pidev.entities.User;

import java.util.List;
import java.util.Optional;

public interface IGestionUser {
    List<User> findAll();
    List<User> findAllActive();
    User add(User user);
    User update(User user);
    User findById (Integer id);
    Optional<User> findByEmail (String email);
    void delete(Integer id);
    User findUserBymail(String email);

}
