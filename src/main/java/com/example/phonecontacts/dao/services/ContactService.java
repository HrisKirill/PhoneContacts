package com.example.phonecontacts.dao.services;

import com.example.phonecontacts.dao.interfaces.IContactDao;
import com.example.phonecontacts.dao.repositories.ContactRepository;
import com.example.phonecontacts.entities.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContactService implements IContactDao {

    private final ContactRepository repository;

    @Autowired
    public ContactService(ContactRepository repository) {
        this.repository = repository;
    }

    @Override
    public Contact create(Contact entity) {
        Optional<Contact> contactOptional = repository.findById(entity.getId());
        if (contactOptional.isEmpty()) {
            return repository.save(entity);
        } else {
            throw new IllegalArgumentException("Contact is already created");
        }
    }

    @Override
    public Contact update(Contact entity) {
        Optional<Contact> contactOptional = repository.findById(entity.getId());
        if (contactOptional.isPresent()) {

            Contact target = contactOptional.get();
            target.setUser(entity.getUser());
            target.setName(entity.getName());
            target.setContactEmailList(entity.getContactEmailList());
            target.setContactPhoneNumbers(entity.getContactPhoneNumbers());

            return repository.save(target);
        } else {
            throw new IllegalArgumentException("Unable to update contact");
        }
    }

    @Override
    public Optional<Contact> getById(Long id) {
        return repository.findById(id);
    }

    @Override
    public String delete(Long entityId) {
        Optional<Contact> contactOptional = repository.findById(entityId);
        if (contactOptional.isPresent()) {
            repository.delete(contactOptional.get());
            return "Contact with id + " + entityId + " deleted successfully";
        } else {
            throw new IllegalArgumentException("Unable to delete contact");
        }
    }

    @Override
    public List<Contact> findAll() {
        return repository.findAll();
    }
}
