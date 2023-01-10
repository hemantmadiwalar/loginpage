package com.example.login.service;


import com.example.login.entity.UserEntity;
import com.example.login.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Component
public class UserService {
    @Autowired
    UserRepository userRepository;

    PasswordEncoder passwordEncoder;

    public UserService(UserRepository repo) {
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public List<UserEntity> getAllUser(){
        return userRepository.findAll();
    }

    public Optional<UserEntity> getUserById(int id){
        return userRepository.findById(id);
    }

//    public UserEntity addUser(UserEntity user){
//
//            String encodedPassword = this.passwordEncoder.encode(user.getPassword());
//            user.setPassword(encodedPassword);
//            return userRepository.save(user);
//    }

    public String signUp(UserEntity users){
        String userName = users.getUserName();
        List<UserEntity> findAllUser = userRepository.findAll();

        boolean presentAlready = false;
        for(UserEntity user : findAllUser){
            if (user.getUserName().equals(userName)) {
                presentAlready = true;
                break;
            }
        }
        if(!presentAlready){
            String encode = passwordEncoder.encode(users.getPassword());
            users.setPassword(encode);

            UserEntity user = userRepository.save(users);
            return "Signup successful";
        }
        return "UserName already present";

    }


    public String updateUser(int id, UserEntity user){
        if(userRepository.existsById(id)){
            String encodedPassword = this.passwordEncoder.encode(user.getPassword());
            user.setPassword(encodedPassword);
            userRepository.save(user);
            return "user updated successfully";
        }
        else{
            return "user does not exist";
        }
    }
    public String deleteUser(int id){
        if (userRepository.existsById(id)){
            userRepository.deleteById(id);
            return "user deleted successfully";
        }
        else{
            return "User id did not found";
        }
    }
}
