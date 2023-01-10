package com.example.login.controller;

import com.example.login.entity.UserEntity;
import com.example.login.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("")
    public String viewHomePage() {
        return "index";
    }

    @GetMapping("/getallusers")
    public List<UserEntity> getAllUser(){
        return userService.getAllUser();
    }

    @GetMapping("/getuserid/{id}")
    public Optional<UserEntity> getByid(@PathVariable("id") int id){
        return userService.getUserById(id);
    }

    @PutMapping("/updateuser/{id}")
    public String updateUser(@PathVariable("id") int id, @RequestBody UserEntity user){
        return userService.updateUser(id, user);
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new UserEntity());

        return "signup_form";
    }

    @PostMapping("/signup")
    public String signup(@RequestBody UserEntity users){
         userService.signUp(users);
         return "register_success";
    }

//    @PostMapping("/adduser")
//    public UserEntity addUser(@RequestBody UserEntity user){
//            return userService.addUser(user);
//    }

    @DeleteMapping("/deleteuser/{id}")
    public String deleteUser(@PathVariable("id") int id){
        return userService.deleteUser(id);
    }
}


