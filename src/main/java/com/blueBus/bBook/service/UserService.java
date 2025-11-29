package com.blueBus.bBook.service;


import com.blueBus.bBook.model.User;
import com.blueBus.bBook.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;
    private PasswordEncoder pwd = new BCryptPasswordEncoder();



    public boolean saveNewUser(User user){
        try{
            if(userRepo.findByEmail(user.getEmail()) != null){

            }
            user.setPassword(pwd.encode(user.getPassword()));
            user.setUserName(user.getUserName());
            user.setRole(Arrays.asList("USER"));
            userRepo.save(user);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void saveAdmin(User user){
        user.setPassword(pwd.encode(user.getPassword()));
        user.setUserName(user.getUserName());
        user.setRole(Arrays.asList("USER","ADMIN"));
        userRepo.save(user);
    }

    public User findByUserName(String userName){
        return userRepo.findByUserName(userName);
    }

    public User findByEmail(String email){
        return userRepo.findByEmail(email);
    }


}
