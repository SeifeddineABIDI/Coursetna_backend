package tn.esprit.pidev.services;

import tn.esprit.pidev.entities.User;
import tn.esprit.pidev.repository.IUserRepository;
import tn.esprit.pidev.repository.ressources.IRessourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GestionUserImpl implements IGestionUser{

    @Autowired
    IUserRepository ur;
    @Autowired
    IRessourceRepository ressourceRepository;
    @Override
    public List<User> getAll(){return ur.findAll();}

    @Override
    public User add(User user)
    {return ur.save(user);}
    @Override
    public  User update(User user)
    {return ur.save(user);}
    @Override
    public User getById(Long id)
    {return ur.getById(id);}






}
