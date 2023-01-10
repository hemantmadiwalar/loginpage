package com.example.login.servicetest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.login.entity.UserEntity;
import com.example.login.service.UserSecurityService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {UserSecurityService.class})
@ExtendWith(SpringExtension.class)
class UserSecurityServiceTest {
    @Autowired
    private UserSecurityService userSecurityService;


    @Test
    void testGenerateJwtToken1() {
        UserEntity userEntity = mock(UserEntity.class);
        when(userEntity.getUserId()).thenReturn(123);
        when(userEntity.getUserName()).thenReturn("rossgeller");
        doNothing().when(userEntity).setPassword((String) any());
        doNothing().when(userEntity).setUserId(anyInt());
        doNothing().when(userEntity).setUserName((String) any());
        userEntity.setPassword("ross@123");
        userEntity.setUserId(123);
        userEntity.setUserName("rossgeller");
        userSecurityService.GenerateJwtToken(userEntity);
        verify(userEntity).getUserId();
        verify(userEntity).getUserName();
        verify(userEntity).setPassword((String) any());
        verify(userEntity).setUserId(anyInt());
        verify(userEntity).setUserName((String) any());
    }


    @Test
    void testVerifyJwtToken() throws Exception {
        assertFalse(userSecurityService.VerifyJwtToken("2022-03-01"));
        assertFalse(userSecurityService.VerifyJwtToken(""));
    }
}
