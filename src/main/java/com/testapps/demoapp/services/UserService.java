package com.testapps.demoapp.services;

import com.testapps.demoapp.domain.User;
import com.testapps.demoapp.exceptions.UsernameAlreadyExistsException;
import com.testapps.demoapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public User saveUser(User newUser){

       try {
           newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
           // Username has to be unique
           newUser.setUsername(newUser.getUsername());
           // Make sure the password and confirmationPassword match
           // We dont't persist or show the confirm Password
           newUser.setConfirmPassword("");
           return userRepository.save(newUser);
       }catch(Exception err) {
    	   throw new UsernameAlreadyExistsException("Username '"+ newUser.getUsername() + "' already exist");
       }
     
    }

}
