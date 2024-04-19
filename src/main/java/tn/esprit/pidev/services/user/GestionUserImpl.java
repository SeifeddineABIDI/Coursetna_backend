package tn.esprit.pidev.services.user;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.pidev.entities.user.User;
import tn.esprit.pidev.repository.user.IUserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class GestionUserImpl implements IGestionUser {
    @Autowired
    IUserRepository ur;
    @PersistenceContext
    EntityManager entityManager;
    @Override
    public List<User> findAll(){return ur.findAll();}
    @Override
    public User add(User user){return ur.save(user);}

    @Override
    public User update(User user) {
        // Check if the user exists
        Optional<User> optionalUser = ur.findById(user.getId());
        if (optionalUser.isPresent()) {
            // Get the existing user
            User existingUser = optionalUser.get();

            // Update the existing user with the new details
            existingUser.setNom(user.getNom());
            existingUser.setPrenom(user.getPrenom());

            // Set other properties that you want to update

            // Save the updated user
            return ur.save(existingUser);
        } else {
            // Handle the case where the user with the given ID doesn't exist
            throw new IllegalArgumentException("User with ID " + user.getId() + " not found");
        }
    }
    @Override
    public User findById(Integer id){return ur.findById(id).orElse(null);}
    @Override
    public void delete(Integer id) {
        User user = findById(id);
        user.setIsArchived(true);
        ur.save(user);
    }

    @Override
    public User findUserBymail(String email) {
        try {
            return entityManager.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null; // No user found with the given email
        }
    }

    @Override
    public List<User> findAllActive() {
        return entityManager.createQuery("SELECT u FROM User u WHERE u.isArchived = false", User.class).getResultList();
    }
    @Override
    public Optional<User> findByEmail(String user){return ur.findByEmail(user);}

    /*********** forum *******/
    @Override
    public Optional<User> getUserByEmail(String email) {
        return ur.findByEmail(email);
    }

}
