package com.example.phonecontacts.authorization.services;

import com.example.phonecontacts.dao.interfaces.IUserDao;
import com.example.phonecontacts.dao.repositories.ConfirmationTokenRepository;
import com.example.phonecontacts.email.EmailService;
import com.example.phonecontacts.exceptions.APIException;
import com.example.phonecontacts.exceptions.VerifyEmailException;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static com.example.phonecontacts.constants.TestConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AuthServiceTest {

    AuthenticationManager authenticationManager = mock(AuthenticationManager.class);

    IUserDao userDao = mock(IUserDao.class);
    ConfirmationTokenRepository confirmationTokenRepository = mock(ConfirmationTokenRepository.class);

    EmailService emailService = mock(EmailService.class);
    PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);


    AuthService authService = new AuthService(authenticationManager, confirmationTokenRepository, emailService, userDao, passwordEncoder);

    @Test
    void loginTest() {
        when(userDao.findByUsernameOrEmail(getLoginDto().getLogin(), getLoginDto().getLogin()))
                .thenReturn(Optional.of(getTestUser()));
        assertEquals("User signed-in successfully!", authService.login(getLoginDto()));
    }

    @Test
    void loginBadTest() {
        when(userDao.findByUsernameOrEmail(getLoginDto().getLogin(), getLoginDto().getLogin()))
                .thenReturn(Optional.empty());
        assertThrows(VerifyEmailException.class, () -> authService.login(getLoginDto()));
    }

    @Test
    void registerTest() {
        when(userDao.existsByUsername(getSignupDto().getUsername())).thenReturn(false);
        when(userDao.existsByEmail(getSignupDto().getEmail())).thenReturn(false);
        assertEquals("Verify email by the link sent on your email address", authService.register(getSignupDto()));
    }

    @Test
    void registerBadTest() {
        when(userDao.existsByUsername(getSignupDto().getUsername())).thenReturn(true);
        when(userDao.existsByEmail(getSignupDto().getEmail())).thenReturn(false);
        assertThrows(APIException.class, () -> authService.register(getSignupDto()));

        when(userDao.existsByUsername(getSignupDto().getUsername())).thenReturn(false);
        when(userDao.existsByEmail(getSignupDto().getEmail())).thenReturn(true);
        assertThrows(APIException.class, () -> authService.register(getSignupDto()));
    }

    @Test
    void confirmEmailTest() {
        when(confirmationTokenRepository.findByConfirmationToken(getTestToken()
                .getConfirmationToken())).thenReturn(Optional.of(getTestToken()));
        when(userDao.findUserByEmail(getTestUser().getEmail())).thenReturn(Optional.of(getTestUser()));
        when(userDao.update(getTestUser(), getTestUser().getId())).thenReturn(getTestUser());
        assertEquals("Email verified successfully!",
                authService.confirmEmail(getTestToken().getConfirmationToken()));
    }

    @Test
    void confirmEmailBadTest() {
        when(confirmationTokenRepository.findByConfirmationToken(getTestToken()
                .getConfirmationToken())).thenReturn(Optional.empty());
        assertThrows(VerifyEmailException.class, () -> authService.confirmEmail(getTestToken().getConfirmationToken()));
    }
}