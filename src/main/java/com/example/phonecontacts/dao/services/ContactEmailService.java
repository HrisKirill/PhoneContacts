package com.example.phonecontacts.dao.services;

import com.example.phonecontacts.dao.interfaces.IContactEmailDao;
import com.example.phonecontacts.dao.repositories.ContactEmailRepository;
import com.example.phonecontacts.entities.ContactEmail;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContactEmailService implements IContactEmailDao {

    private final ContactEmailRepository repository;

    @Autowired
    public ContactEmailService(ContactEmailRepository repository) {
        this.repository = repository;
    }

    @Override
    public ContactEmail create(ContactEmail entity) {
        Optional<ContactEmail> contactEmailOptional = repository.findByEmail(entity.getEmail());
        if (contactEmailOptional.isEmpty()) {
            return repository.save(entity);
        } else {
            throw new IllegalArgumentException("Contact email is already created");
        }
    }

    @Override
    public ContactEmail update(ContactEmail entity) {
        Optional<ContactEmail> contactEmailOptional = repository.findById(entity.getId());
        if (contactEmailOptional.isPresent()) {

            ContactEmail target = contactEmailOptional.get();
            target.setEmail(entity.getEmail());
            target.setContact(entity.getContact());

            return repository.save(target);
        } else {
            throw new IllegalArgumentException("Unable to update contact email");
        }
    }

    @Override
    public Optional<ContactEmail> getById(Long id) {
        return repository.findById(id);
    }

    @Override
    public String delete(Long entityId) {
        Optional<ContactEmail> contactEmailOptional = repository.findById(entityId);
        if (contactEmailOptional.isPresent()) {
            repository.delete(contactEmailOptional.get());
            return "Contact email with id + " + entityId + " deleted successfully";
        } else {
            throw new IllegalArgumentException("Unable to delete contact email");
        }
    }

    @Override
    public List<ContactEmail> findAll() {
        return repository.findAll();
    }
}
