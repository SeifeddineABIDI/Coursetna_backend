package com.picloud.picloud.services;

import com.picloud.picloud.entities.User;
import com.picloud.picloud.entities.ressources.Ressource;
import com.picloud.picloud.repository.IUserRepository;
import com.picloud.picloud.repository.ressources.IRessourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
