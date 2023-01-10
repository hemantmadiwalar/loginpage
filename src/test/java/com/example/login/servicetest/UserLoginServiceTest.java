package com.example.login.servicetest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.login.entity.ForgotPassword;
import com.example.login.entity.LoginUser;
import com.example.login.entity.UserEntity;
import com.example.login.repository.UserRepository;

import java.util.ArrayList;

import com.example.login.service.UserLoginService;
import com.example.login.service.UserSecurityService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {UserLoginService.class})
@ExtendWith(SpringExtension.class)
class UserLoginServiceTest {
    @Autowired
    private UserLoginService userLoginService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private UserSecurityService userSecurityService;

    @Test
    void testLoginUser() {
        UserEntity userEntity = new UserEntity();
        userEntity.setPassword("hemant@123");
        userEntity.setUserId(123);
        userEntity.setUserName("hemant");
        when(userRepository.findByUserName((String) any())).thenReturn(userEntity);
        assertEquals("Incorrect password",userLoginService.loginUser(new LoginUser("hemant", "hemant@123")));
        verify(userRepository).findByUserName((String) any());
    }

    @Test
    void testForgotPassword(){
        UserEntity userEntity = new UserEntity();
        userEntity.setPassword("hemant@123");
        userEntity.setUserId(123);
        userEntity.setUserName("hemant");

        UserEntity userEntity1 = new UserEntity();
        userEntity1.setUserName("hemant");
        userEntity1.setPassword("hemant@123");
        userEntity1.setUserId(123);
        when(userRepository.save((UserEntity) any())).thenReturn(userEntity1);
        when(userRepository.findByUserName((String) any())).thenReturn(userEntity);
        assertEquals("New password updated successfully",
                userLoginService.forgotPassword(new ForgotPassword("hemant","hemant@123")));
        verify(userRepository).findByUserName((String) any());
        verify(userRepository).save((UserEntity) any());
    }


    @Test
    void testLogOutUser() {
        assertEquals("Logout successful", userLoginService.logOutUser());
    }
}











////////////////////////////////////
/*

    @Test
    void testLoginUser() {
        when(userRepository.findAll()).thenReturn(new ArrayList<>());
        assertEquals("Username Not Found", userLoginService.loginUser(new LoginUser("rossgeller", "ross@123")));
        verify(userRepository).findAll();
    }

    @Test
    void testLoginUser2() {
        when(userSecurityService.GenerateJwtToken((UserEntity) any())).thenReturn("ABC123");

        UserEntity userEntity = new UserEntity();
        userEntity.setPassword("ross@123");
        userEntity.setUserId(123);
        userEntity.setUserName("rossgeller");

        ArrayList<UserEntity> userEntityList = new ArrayList<>();
        userEntityList.add(userEntity);
        when(userRepository.findAll()).thenReturn(userEntityList);
        assertEquals("Login Successful\nABC123", userLoginService.loginUser(new LoginUser("rossgeller", "ross@123")));
        verify(userSecurityService).GenerateJwtToken((UserEntity) any());
        verify(userRepository).findAll();
    }

    @Test
    void testLoginUser3() {
        when(userSecurityService.GenerateJwtToken((UserEntity) any())).thenReturn("ABC123");
        UserEntity userEntity = mock(UserEntity.class);
        when(userEntity.getPassword()).thenReturn("ross");
        when(userEntity.getUserName()).thenReturn("rossgeller");
        doNothing().when(userEntity).setPassword((String) any());
        doNothing().when(userEntity).setUserId(anyInt());
        doNothing().when(userEntity).setUserName((String) any());
        userEntity.setPassword("ross@123");
        userEntity.setUserId(123);
        userEntity.setUserName("rossgeller");

        ArrayList<UserEntity> userEntityList = new ArrayList<>();
        userEntityList.add(userEntity);
        when(userRepository.findAll()).thenReturn(userEntityList);
        assertEquals("Incorrect Password", userLoginService.loginUser(new LoginUser("rossgeller", "ross@123")));
        verify(userRepository).findAll();
        verify(userEntity).getPassword();
        verify(userEntity).getUserName();
        verify(userEntity).setPassword((String) any());
        verify(userEntity).setUserId(anyInt());
        verify(userEntity).setUserName((String) any());
    }


    @Test
    void testLoginUser4() {
        when(userSecurityService.GenerateJwtToken((UserEntity) any())).thenReturn("ABC123");
        UserEntity userEntity = mock(UserEntity.class);
        when(userEntity.getPassword()).thenReturn("ross@123");
        when(userEntity.getUserName()).thenReturn("joey");
        doNothing().when(userEntity).setPassword((String) any());
        doNothing().when(userEntity).setUserId(anyInt());
        doNothing().when(userEntity).setUserName((String) any());
        userEntity.setPassword("Ross@123");
        userEntity.setUserId(123);
        userEntity.setUserName("rossgeller");

        ArrayList<UserEntity> userEntityList = new ArrayList<>();
        userEntityList.add(userEntity);
        when(userRepository.findAll()).thenReturn(userEntityList);
        assertEquals("Username Not Found", userLoginService.loginUser(new LoginUser("rossgeller", "ross@123")));
        verify(userRepository).findAll();
        verify(userEntity).getUserName();
        verify(userEntity).setPassword((String) any());
        verify(userEntity).setUserId(anyInt());
        verify(userEntity).setUserName((String) any());
    }
 */


