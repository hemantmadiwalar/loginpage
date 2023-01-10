package com.example.login.controller;

import com.example.login.entity.ForgotPassword;
import com.example.login.entity.LoginUser;
import com.example.login.service.UserLoginService;
import com.example.login.service.UserSecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class UserLoginController {
    @Autowired
    UserLoginService loginService;

    @Autowired
    UserSecurityService security;

    @PostMapping("/userlogin")
    public String userLogin(@RequestBody LoginUser login) {
        return loginService.loginUser(login);
    }

    @PostMapping("/forgotPassword")
    public String forgotPassword(@RequestBody ForgotPassword newpwd){
        return loginService.forgotPassword(newpwd);
    }

    @GetMapping("/userlogout")
    public String userLogout(){
        return loginService.logOutUser();
    }

    @PostMapping("/validatetoken")
    public String validTok(@RequestParam("token") String token) throws Exception{
        if(security.VerifyJwtToken(token)){
            return "valid token";
        }
        return "Invalid token";
    }
}
