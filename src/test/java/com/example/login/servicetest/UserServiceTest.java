package com.example.login.servicetest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.login.entity.UserEntity;
import com.example.login.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.login.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {UserService.class})
@ExtendWith(SpringExtension.class)
class UserServiceTest {
    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserService userService;


    @Test
    void testGetAllUser() {
        ArrayList<UserEntity> userEntityList = new ArrayList<>();
        when(userRepository.findAll()).thenReturn(userEntityList);
        List<UserEntity> actualAllUser = userService.getAllUser();
        assertSame(userEntityList, actualAllUser);
        assertTrue(actualAllUser.isEmpty());
        verify(userRepository).findAll();
    }


    @Test
    void testGetUserById() {
        UserEntity userEntity = new UserEntity();
        userEntity.setPassword("ross@123");
        userEntity.setUserId(123);
        userEntity.setUserName("rossgeller");
        Optional<UserEntity> ofResult = Optional.of(userEntity);
        when(userRepository.findById((Integer) any())).thenReturn(ofResult);
        Optional<UserEntity> actualUserById = userService.getUserById(1);
        assertSame(ofResult, actualUserById);
        assertTrue(actualUserById.isPresent());
        verify(userRepository).findById((Integer) any());
    }


//    @Test
//    void testAddUser() {
//        UserEntity userEntity = new UserEntity();
//        userEntity.setPassword("ross@123");
//        userEntity.setUserId(123);
//        userEntity.setUserName("rossgeller");
//        when(userRepository.save((UserEntity) any())).thenReturn(userEntity);
//
//        UserEntity userEntity1 = new UserEntity();
//        userEntity1.setPassword("ross@123");
//        userEntity1.setUserId(123);
//        userEntity1.setUserName("rossgeller");
//        assertSame(userEntity, userService.addUser(userEntity1));
//        verify(userRepository).save((UserEntity) any());
//    }

    @Test
    void testSignUp(){
        UserEntity userEntity = new UserEntity();
        userEntity.setPassword("ross@123");
        userEntity.setUserId(123);
        userEntity.setUserName("rossgeller");
        when(userRepository.save((UserEntity) any())).thenReturn(userEntity);
        when(userRepository.findAll()).thenReturn(new ArrayList<>());

        UserEntity userEntity1 = new UserEntity();
        userEntity1.setPassword("ross@123");
        userEntity1.setUserId(123);
        userEntity1.setUserName("rossgeller");
        assertEquals("Signup successful", userService.signUp(userEntity1));
        verify(userRepository).save((UserEntity) any());
        verify(userRepository).findAll();
    }

    @Test
    void testSignUp2(){
        UserEntity userEntity = new UserEntity();
        userEntity.setPassword("ross@123");
        userEntity.setUserId(123);
        userEntity.setUserName("rossgeller");

        UserEntity userEntity1 = new UserEntity();
        userEntity1.setPassword("ross@123");
        userEntity1.setUserId(123);
        userEntity1.setUserName("rossgeller");

        ArrayList<UserEntity> userEntities = new ArrayList<>();
        userEntities.add(userEntity1);
        when(userRepository.save((UserEntity) any())).thenReturn(userEntity);
        when(userRepository.findAll()).thenReturn(userEntities);

        UserEntity userEntity2 = new UserEntity();
        userEntity2.setPassword("ross@123");
        userEntity2.setUserId(123);
        userEntity2.setUserName("rossgeller");
        assertEquals("UserName already present", userService.signUp(userEntity2));
        verify(userRepository).findAll();
    }

    @Test
    void testUpdateUser() {
        UserEntity userEntity = new UserEntity();
        userEntity.setPassword("ross@123");
        userEntity.setUserId(123);
        userEntity.setUserName("rossgeller");
        when(userRepository.save((UserEntity) any())).thenReturn(userEntity);
        when(userRepository.existsById((Integer) any())).thenReturn(true);

        UserEntity userEntity1 = new UserEntity();
        userEntity1.setPassword("ross@123");
        userEntity1.setUserId(123);
        userEntity1.setUserName("rossgeller");
        assertEquals("user updated successfully", userService.updateUser(1, userEntity1));
        verify(userRepository).existsById((Integer) any());
        verify(userRepository).save((UserEntity) any());
    }

    @Test
    void testUpdateUser2() {
        UserEntity userEntity = new UserEntity();
        userEntity.setPassword("ross@123");
        userEntity.setUserId(123);
        userEntity.setUserName("rossgeller");
        when(userRepository.save((UserEntity) any())).thenReturn(userEntity);
        when(userRepository.existsById((Integer) any())).thenReturn(false);

        UserEntity userEntity1 = new UserEntity();
        userEntity1.setPassword("ross@123");
        userEntity1.setUserId(123);
        userEntity1.setUserName("rossgeller");
        assertEquals("user does not exist", userService.updateUser(1, userEntity1));
        verify(userRepository).existsById((Integer) any());
    }

    @Test
    void testDeleteUser() {
        doNothing().when(userRepository).deleteById((Integer) any());
        when(userRepository.existsById((Integer) any())).thenReturn(true);
        assertEquals("user deleted successfully", userService.deleteUser(1));
        verify(userRepository).existsById((Integer) any());
        verify(userRepository).deleteById((Integer) any());
    }

    @Test
    void testDeleteUser2() {
        doNothing().when(userRepository).deleteById((Integer) any());
        when(userRepository.existsById((Integer) any())).thenReturn(false);
        assertEquals("User id did not found", userService.deleteUser(1));
        verify(userRepository).existsById((Integer) any());
    }
}
