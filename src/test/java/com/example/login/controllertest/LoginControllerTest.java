package com.example.login.controllertest;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import com.example.login.controller.UserLoginController;
import com.example.login.entity.LoginUser;
import com.example.login.service.UserLoginService;
import com.example.login.service.UserSecurityService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

//@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {UserLoginController.class})
@ExtendWith(SpringExtension.class)
class LoginControllerTest {
    @Autowired
    private UserLoginController userLoginController;

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @MockBean
    private UserLoginService userLoginService;

    @MockBean
    private UserSecurityService userSecurityService;

    @Test
    void testUserLogout() throws Exception {
        when(userLoginService.logOutUser()).thenReturn("Log Out User");
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/login/userlogout");
        MockMvcBuilders.standaloneSetup(userLoginController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Log Out User"));
    }


    @Test
    void testValidTok() throws Exception {
        when(userSecurityService.VerifyJwtToken((String) any())).thenReturn(true);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/login/validatetoken")
                .param("token", "foo");
        MockMvcBuilders.standaloneSetup(userLoginController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("valid token"));
    }


    @Test
    void testValidTok2() throws Exception {
        when(userSecurityService.VerifyJwtToken((String) any())).thenReturn(false);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/login/validatetoken")
                .param("token", "foo");
        MockMvcBuilders.standaloneSetup(userLoginController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Invalid token"));
    }

   @Test
    void testLogin() throws Exception {
        when(userLoginService.loginUser((LoginUser) any())).thenReturn("Login Successful");

        LoginUser loginUser= new LoginUser();
        loginUser.setPassword("hemant@123");
        loginUser.setUserName("hemant");
        String content = (new ObjectMapper()).writeValueAsString(loginUser);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(("/login/userlogin"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(userLoginController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Login Successful"));
    }



}
