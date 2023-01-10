package com.example.login.controllertest;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.when;

import com.example.login.controller.UserController;
import com.example.login.entity.UserEntity;
import com.example.login.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {UserController.class})
@ExtendWith(SpringExtension.class)
class UserControllerTest {
    @Autowired
    private UserController userController;

    @MockBean
    private UserService userService;

    @Test
    void testGetAllUser() throws Exception {
        when(userService.getAllUser()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/user/getallusers");
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }


    @Test
    void testGetAllUser2() throws Exception {
        when(userService.getAllUser()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/user/getallusers");
        getResult.characterEncoding("Encoding");
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testUpdateUser() throws Exception {
        when(userService.updateUser(anyInt(), (UserEntity) any())).thenReturn("2020-03-01");

        UserEntity userEntity = new UserEntity();
        userEntity.setPassword("Hemant@123");
        userEntity.setUserId(123);
        userEntity.setUserName("hemantmadiwalar");
        String content = (new ObjectMapper()).writeValueAsString(userEntity);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/user/updateuser/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("2020-03-01"));
    }

    @Test
    void testSignup() throws Exception {
        when(userService.signUp((UserEntity) any())).thenReturn("register_success");

        UserEntity userEntity = new UserEntity();
        userEntity.setPassword("hemant@123");
        userEntity.setUserId(123);
        userEntity.setUserName("hemant");
        String content = (new ObjectMapper()).writeValueAsString(userEntity);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/user/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("register_success"));

    }


//    @Test
//    void testAddUser() throws Exception {
//        UserEntity userEntity = new UserEntity();
//        userEntity.setPassword("Hemant@123");
//        userEntity.setUserId(123);
//        userEntity.setUserName("hemantmadiwalar");
//        when(userService.addUser((UserEntity) any())).thenReturn(userEntity);
//
//        UserEntity userEntity1 = new UserEntity();
//        userEntity1.setPassword("Hemant@123");
//        userEntity1.setUserId(123);
//        userEntity1.setUserName("hemantmadiwalar");
//        String content = (new ObjectMapper()).writeValueAsString(userEntity1);
//        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/user/adduser")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(content);
//        MockMvcBuilders.standaloneSetup(userController)
//                .build()
//                .perform(requestBuilder)
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
//                .andExpect(MockMvcResultMatchers.content()
//                        .string("{\"userId\":123,\"userName\":\"hemantmadiwalar\",\"password\":\"Hemant@123\"}"));
//    }


    @Test
    void testDeleteUser() throws Exception {
        when(userService.deleteUser(anyInt())).thenReturn("Delete User");
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/user/deleteuser/{id}", 1);
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Delete User"));
    }


    @Test
    void testGetByid() throws Exception {
        UserEntity userEntity = new UserEntity();
        userEntity.setPassword("Hemant@123");
        userEntity.setUserId(123);
        userEntity.setUserName("hemantmadiwalar");
        Optional<UserEntity> ofResult = Optional.of(userEntity);
        when(userService.getUserById(anyInt())).thenReturn(ofResult);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/user/getuserid/{id}", 1);
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"userId\":123,\"userName\":\"hemantmadiwalar\",\"password\":\"Hemant@123\"}"));
    }
}
