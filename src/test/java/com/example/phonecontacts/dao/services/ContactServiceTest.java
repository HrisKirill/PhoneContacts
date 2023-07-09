package com.example.phonecontacts.dao.services;

import com.example.phonecontacts.dao.repositories.ContactRepository;
import com.example.phonecontacts.dao.repositories.UserRepository;
import com.example.phonecontacts.entities.Contact;
import com.example.phonecontacts.entities.Image;
import com.example.phonecontacts.exceptions.DuplicateException;
import com.example.phonecontacts.exceptions.IllegalImageFormatException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.example.phonecontacts.constants.TestConstants.*;
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
        Contact contact = getTestContact();
        contact.setImage(getTestImage());
        contact.getImage().setName("test.png");
        when(contactRepository.findByName(contact.getName())).thenReturn(Optional.empty());
        when(contactRepository.findAll()).thenReturn(Collections.emptyList());
        when(contactRepository.save(contact)).thenReturn(contact);
        assertEquals(contact, contactService.create(contact));
    }

    @Test
    void createBadTest() {
        setUpAuthentication();
        Contact contact = getTestContact();
        contact.setId(2L);
        when(contactRepository.findByName(getTestContact().getName())).thenReturn(Optional.empty());
        when(contactRepository.findAllByUser(getTestUser())).thenReturn(List.of(contact));
        assertThrows(DuplicateException.class, () -> contactService.create(getTestContact()));
        contact.setEmails(Collections.emptySet());
        assertThrows(DuplicateException.class, () -> contactService.create(getTestContact()));
        when(contactRepository.findByName(getTestContact().getName())).thenReturn(Optional.of(getTestContact()));
        contact.setPhones(Collections.emptySet());
        assertThrows(IllegalArgumentException.class, () -> contactService.create(getTestContact()));
        contact.setImage(getTestImage());
        assertThrows(IllegalImageFormatException.class, () -> contactService.create(contact));
    }


    @Test
    void updateTest() {
        setUpAuthentication();
        when(contactRepository.findAllByUser(getTestUser())).thenReturn(List.of(getTestContact()));
        when(contactRepository.findById(getTestContact().getId())).thenReturn(Optional.of(getTestContact()));
        when(contactRepository.existsContactByUser(getTestUser())).thenReturn(true);
        when(contactRepository.findAll()).thenReturn(Collections.emptyList());
        when(contactRepository.save(getTestContact())).thenReturn(getTestContact());
        assertEquals(getTestContact(), contactService.update(getTestContact(), getTestContact().getId()));
    }

    @Test
    void updateBadTest() {
        setUpAuthentication();
        Contact contact = getTestContact();
        contact.setId(2L);
        when(contactRepository.findByName(getTestContact().getName())).thenReturn(Optional.of(getTestContact()));
        when(contactRepository.existsContactByUser(getTestUser())).thenReturn(true);

        when(contactRepository.findAllByUser(getTestUser())).thenReturn(List.of(contact));
        assertThrows(DuplicateException.class, () -> contactService.update(getTestContact(), getTestUser().getId()));

        contact.setEmails(Collections.emptySet());
        assertThrows(DuplicateException.class, () -> contactService.update(getTestContact(), getTestUser().getId()));

        contact.setPhones(Collections.emptySet());
        contact.setImage(getTestImage());
        contact.setId(1L);
        assertThrows(IllegalImageFormatException.class, () -> contactService.update(contact,getTestUser().getId()));

        when(contactRepository.findByName(getTestContact().getName())).thenReturn(Optional.empty());
        when(contactRepository.existsContactByUser(getTestUser())).thenReturn(false);
        assertThrows(IllegalArgumentException.class, () -> contactService.update(getTestContact(), getTestUser().getId()));
    }

    @Test
    void deleteTest() {
        setUpAuthentication();
        when(contactRepository.findAllByUser(getTestUser())).thenReturn(List.of(getTestContact()));
        assertEquals("Contact with id 1 deleted successfully", contactService.delete(getTestContact().getId()));
    }

    @Test
    void deleteBadTest() {
        setUpAuthentication();
        when(contactRepository.findAllByUser(getTestUser())).thenReturn(Collections.emptyList());
        assertThrows(IllegalArgumentException.class, () -> contactService.delete(getTestContact().getId()));
    }


    @Test
    void getByIdTest() {
        setUpAuthentication();
        when(contactRepository.findAllByUser(getTestUser())).thenReturn(List.of(getTestContact()));
        assertEquals(Optional.of(getTestContact()), contactService.getById(1L));
    }

    @Test
    void getByIdBadTest() {
        setUpAuthentication();
        when(contactRepository.findAllByUser(getTestUser())).thenReturn(Collections.emptyList());
        assertEquals(Optional.empty(), contactService.getById(1L));
    }


    void setUpAuthentication() {
        when(secCont.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(secCont);
        when(authentication.getName()).thenReturn(getTestUser().getUserName());
        when(userService.findByUsernameOrEmail(getTestUser().getUserName(), getTestUser().getUserName()))
                .thenReturn(Optional.of(getTestUser()));
    }

}