package com.example.phonecontacts.authorization.services;

import com.example.phonecontacts.dao.interfaces.IUserDao;
import com.example.phonecontacts.exceptions.APIException;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.example.phonecontacts.constants.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AuthServiceTest {

    AuthenticationManager authenticationManager = mock(AuthenticationManager.class);

    Authentication authentication = mock(Authentication.class);
    IUserDao userDao = mock(IUserDao.class);

    PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);

    SecurityContext secCont = mock(SecurityContext.class);

    AuthService authService = new AuthService(authenticationManager, userDao, passwordEncoder);

    @Test
    void loginTest() {
    /*    when(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                getLoginDto().getLogin(), getLoginDto().getPassword()))).thenReturn(authentication);
        secCont.setAuthentication(authentication);*/
        assertEquals("User signed-in successfully!", authService.login(getLoginDto()));
    }

    @Test
    void registerTest() {
        when(userDao.existsByUsername(getSignupDto().getUsername())).thenReturn(false);
        when(userDao.existsByEmail(getSignupDto().getEmail())).thenReturn(false);
        assertEquals("User registered successfully!", authService.register(getSignupDto()));
    }

    @Test
    void registerBadTest(){
        when(userDao.existsByUsername(getSignupDto().getUsername())).thenReturn(true);
        when(userDao.existsByEmail(getSignupDto().getEmail())).thenReturn(false);
        assertThrows(APIException.class,()-> authService.register(getSignupDto()));

        when(userDao.existsByUsername(getSignupDto().getUsername())).thenReturn(false);
        when(userDao.existsByEmail(getSignupDto().getEmail())).thenReturn(true);
        assertThrows(APIException.class,()-> authService.register(getSignupDto()));
    }
}