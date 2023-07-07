package com.example.phonecontacts.dao.services;

import com.example.phonecontacts.dao.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Optional;

import static com.example.phonecontacts.constants.TestConstants.getTestUser;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserServiceTest {

    UserRepository userRepository = mock(UserRepository.class);

    UserService userService = new UserService(userRepository);

    Authentication authentication = mock(Authentication.class);

    SecurityContext secCont = mock(SecurityContext.class);

    @BeforeEach
    void init_mocks() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createTest() {
        when(userRepository.findUserByUserNameOrEmail(getTestUser().getUserName(), getTestUser().getUserName()))
                .thenReturn(Optional.empty());
        when(userRepository.save(getTestUser())).thenReturn(getTestUser());
        assertEquals(getTestUser(), userService.create(getTestUser()));
    }

    @Test
    void createBadTest() {
        when(userRepository.findUserByUserNameOrEmail(getTestUser().getUserName(), getTestUser().getEmail()))
                .thenReturn(Optional.of(getTestUser()));

        assertThrows(IllegalArgumentException.class, () -> userService.create(getTestUser()));
    }

    @Test
    void updateTest() {
        when(userRepository.findById(getTestUser().getId())).thenReturn(Optional.of(getTestUser()));
        when(userRepository.save(getTestUser())).thenReturn(getTestUser());
        assertEquals(getTestUser(), userService.update(getTestUser()));
    }

    @Test
    void updateBadTest() {
        when(userRepository.findById(getTestUser().getId())).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> userService.update(getTestUser()));
    }

    @Test
    void getByIdTest() {
        when(userService.getById(getTestUser().getId())).thenReturn(Optional.of(getTestUser()));
    }

    @Test
    void deleteTest() {
        when(userRepository.findById(getTestUser().getId())).thenReturn(Optional.of(getTestUser()));
        assertEquals("User with id 1 deleted successfully", userService.delete(getTestUser().getId()));
    }

    @Test
    void deleteBadTest() {
        when(userRepository.findById(getTestUser().getId())).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> userService.delete(getTestUser().getId()));
    }

    @Test
    void findAll() {
        when(userRepository.findAll()).thenReturn(List.of(getTestUser()));
        assertEquals(List.of(getTestUser()), userService.findAll());
    }

    @Test
    void existsByUsernameTest() {
        when(userService.existsByUsername(getTestUser().getUserName())).thenReturn(true);
    }

    @Test
    void existsByEmailTest() {
        when(userService.existsByEmail(getTestUser().getEmail())).thenReturn(true);
    }

    @Test
    void getCurrentUserBadTest(){
        when(secCont.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(secCont);
        when(authentication.getName()).thenReturn(getTestUser().getUserName());
        when(userService.findByUsernameOrEmail(getTestUser().getUserName(), getTestUser().getUserName()))
                .thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class,()->userService.getCurrentUser());
    }
}