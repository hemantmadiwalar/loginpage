package com.example.login.service;


import com.example.login.entity.ForgotPassword;
import com.example.login.entity.LoginUser;
import com.example.login.entity.UserEntity;
import com.example.login.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;

//@Service
@Transactional
@Component
public class UserLoginService {

    @Autowired
    UserRepository userRepository;


    @Autowired
    UserSecurityService security;

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserLoginService(UserRepository repo) {
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public String loginUser(LoginUser loginUser) {

        UserEntity users = userRepository.findByUserName(loginUser.getUserName());
        if (users != null) {

            if (passwordEncoder.matches(loginUser.getPassword(),users.getPassword())){
                try {
                    String token = security.GenerateJwtToken(users);
                    return "Login Successful\n"+token;
                }catch (Exception e){
                    return "failed to generate JWT token";
                }
            }
            else{
                return "Incorrect password";
            }
        }
        return  "User not present";
    }

    public String forgotPassword(ForgotPassword forgotPassword) {
        UserEntity users = userRepository.findByUserName(forgotPassword.getUserName());
        if (users != null) {
            System.out.println("2....... " + forgotPassword.getPassword());

            users.setPassword(passwordEncoder.encode(forgotPassword.getPassword()));
            userRepository.save(users);

            return "New password updated successfully";
        }

        return "User not found";
    }


//    public String changePassword(ChangePassword newpwd){
//        List<UserEntity> list = userRepository.findAll();
//        for (UserEntity ue:list) {
//            if (ue.getUserName().equals(newpwd.getUserName())){
//                if(ue.getPassword().equals(newpwd.getOldPassword())){
//                    if(!newpwd.getOldPassword().equals(newpwd.getNewPassword())){
//                        ue.setPassword(newpwd.getNewPassword());
//                        userRepository.save(ue);
//                        return "New Password updated Successfully";
//                    }else{
//                        return "Old and New Password are same!";
//                    }
//                }else{
//                    return "Incorrect Old Password";
//                }
//            }
//        }
//        return "UserName Not Found";
//    }
    public String logOutUser(){
        return "Logout successful";
    }

}
