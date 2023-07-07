package com.example.phonecontacts.dao.services;

import com.example.phonecontacts.dao.repositories.ContactRepository;
import com.example.phonecontacts.dao.repositories.UserRepository;
import com.example.phonecontacts.entities.Contact;
import com.example.phonecontacts.entities.ContactEmail;
import com.example.phonecontacts.entities.ContactPhoneNumber;
import com.example.phonecontacts.entities.User;
import com.example.phonecontacts.exceptions.DuplicateException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.example.phonecontacts.constants.TestConstants.getTestContact;
import static com.example.phonecontacts.constants.TestConstants.getTestUser;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class ContactServiceTest {

    ContactRepository contactRepository = mock(ContactRepository.class);

    UserRepository userRepository = mock(UserRepository.class);

    UserService userService = new UserService(userRepository);

    Authentication authentication = mock(Authentication.class);

    SecurityContext secCont = mock(SecurityContext.class);

    ContactService contactService = new ContactService(contactRepository, userService);


    @BeforeEach
    void init_mocks() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createTest() {
        setUpAuthentication();
        when(contactRepository.findByName(getTestContact().getName())).thenReturn(Optional.empty());
        when(contactRepository.findAll()).thenReturn(Collections.emptyList());
        when(contactRepository.save(getTestContact())).thenReturn(getTestContact());
        assertEquals(getTestContact(), contactService.create(getTestContact()));
    }

    @Test
    void createBadTest() {
        setUpAuthentication();
        Contact contact = getTestContact();
        contact.setName("Andrew");
        when(contactRepository.findByName(getTestContact().getName())).thenReturn(Optional.empty());
        when(contactRepository.findAllByUser(getTestUser())).thenReturn(List.of(contact));
        assertThrows(DuplicateException.class, () -> contactService.create(getTestContact()));
        contact.setEmails(Collections.emptySet());
        assertThrows(DuplicateException.class, () -> contactService.create(getTestContact()));
        when(contactRepository.findByName(getTestContact().getName())).thenReturn(Optional.of(getTestContact()));
        contact.setPhones(Collections.emptySet());
        assertThrows(IllegalArgumentException.class, () -> contactService.create(getTestContact()));
    }


    @Test
    void updateTest(){
        setUpAuthentication();
        when(contactRepository.findByName(getTestContact().getName())).thenReturn(Optional.of(getTestContact()));
        when(contactRepository.existsContactByUser(getTestUser())).thenReturn(true);
        when(contactRepository.findAll()).thenReturn(Collections.emptyList());
        when(contactRepository.save(getTestContact())).thenReturn(getTestContact());
        assertEquals(getTestContact(), contactService.update(getTestContact()));
    }

    @Test
    void updateBadTest() {
        setUpAuthentication();
        Contact contact = getTestContact();
        contact.setName("Andrew");
        when(contactRepository.findByName(getTestContact().getName())).thenReturn(Optional.of(getTestContact()));
        when(contactRepository.existsContactByUser(getTestUser())).thenReturn(true);
        when(contactRepository.findAllByUser(getTestUser())).thenReturn(List.of(contact));
        assertThrows(DuplicateException.class, () -> contactService.update(getTestContact()));
        contact.setEmails(Collections.emptySet());
        assertThrows(DuplicateException.class, () -> contactService.update(getTestContact()));
        when(contactRepository.findByName(getTestContact().getName())).thenReturn(Optional.empty());
        contact.setPhones(Collections.emptySet());
        assertThrows(IllegalArgumentException.class, () -> contactService.update(getTestContact()));
    }

    @Test
    void deleteTest(){
        setUpAuthentication();
        when(contactRepository.findAllByUser(getTestUser())).thenReturn(List.of(getTestContact()));
        assertEquals("Contact with id 1 deleted successfully",contactService.delete(getTestContact().getId()));
    }

    @Test
    void deleteBadTest(){
        setUpAuthentication();
        when(contactRepository.findAllByUser(getTestUser())).thenReturn(Collections.emptyList());
        assertThrows(IllegalArgumentException.class,()-> contactService.delete(getTestContact().getId()));
    }


    @Test
    void getByIdTest() {
        when(contactService.getById(getTestContact().getId())).thenReturn(Optional.of(getTestContact()));
    }


    void setUpAuthentication() {
        when(secCont.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(secCont);
        when(authentication.getName()).thenReturn(getTestUser().getUserName());
        when(userService.findByUsernameOrEmail(getTestUser().getUserName(), getTestUser().getUserName()))
                .thenReturn(Optional.of(getTestUser()));
    }

}