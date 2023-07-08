package com.example.phonecontacts.controller;

import com.example.phonecontacts.dao.interfaces.IContactDao;
import com.example.phonecontacts.dao.interfaces.IUserDao;
import com.example.phonecontacts.dto.ContactDto;
import com.example.phonecontacts.entities.Contact;
import com.example.phonecontacts.mapper.ContactToContactDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.phonecontacts.mapper.ContactDtoToContactMapper.convertDtoContact;

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
    public ResponseEntity<Contact> createContact(@RequestBody ContactDto contactDto) {
        return new ResponseEntity<>(dao.create(convertDtoContact(contactDto,
                userDao.getCurrentUser())), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Contact> updateContact(@RequestBody ContactDto contactDto,@PathVariable Long id) {
        return new ResponseEntity<>(dao.update(convertDtoContact(contactDto,
                userDao.getCurrentUser()),id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteContact(@PathVariable Long id) {
        return new ResponseEntity<>(dao.delete(id), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ContactDto>> getAllContacts() {
        return new ResponseEntity<>(dao.findAll().stream()
                .map(ContactToContactDto::contactToContactDto)
                .toList(), HttpStatus.OK);
    }
}
