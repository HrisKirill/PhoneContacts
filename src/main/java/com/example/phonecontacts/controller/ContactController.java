package com.example.phonecontacts.controller;

import com.example.phonecontacts.dao.interfaces.IContactDao;
import com.example.phonecontacts.dao.interfaces.IUserDao;
import com.example.phonecontacts.dto.ContactDto;
import com.example.phonecontacts.entities.Contact;
import com.example.phonecontacts.entities.User;
import com.example.phonecontacts.mapper.ContactDtoToContactMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/contacts")
public class ContactController {
    private final IContactDao dao;

    private final IUserDao userDao;

    @Autowired
    public ContactController(IContactDao dao, IUserDao userDao) {
        this.dao = dao;
        this.userDao = userDao;
    }

    @PostMapping
    ResponseEntity<Contact> createContact(@RequestBody ContactDto contactDto, Authentication authentication) {
        String login = authentication.getName();
        Optional<User> userOptional = userDao.findByUsernameOrEmail(login, login);
        User user = userOptional.orElseGet(userOptional::orElseThrow);
        Contact contact = ContactDtoToContactMapper.convertDtoContact(contactDto, user);
        return new ResponseEntity<>(dao.create(contact), HttpStatus.OK);
    }

    @PutMapping
    ResponseEntity<Contact> updateContact(@RequestBody Contact contact) {
        return new ResponseEntity<>(dao.update(contact), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<String> deleteContact(@PathVariable Long id) {
        return new ResponseEntity<>(dao.delete(id), HttpStatus.OK);
    }

    @GetMapping("/all")
    ResponseEntity<List<Contact>> getAllContacts() {
        return new ResponseEntity<>(dao.findAll(), HttpStatus.OK);
    }
}
