/*
package com.example.phonecontacts.dao.services;

import com.example.phonecontacts.dao.interfaces.IContactPhoneNumberDao;
import com.example.phonecontacts.dao.repositories.ContactPhoneNumberRepository;
import com.example.phonecontacts.entities.ContactPhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContactPhoneNumberService implements IContactPhoneNumberDao {

    private final ContactPhoneNumberRepository repository;

    @Autowired
    public ContactPhoneNumberService(ContactPhoneNumberRepository repository) {
        this.repository = repository;
    }

    @Override
    public ContactPhoneNumber create(ContactPhoneNumber entity) {
        Optional<ContactPhoneNumber> contactPhoneNumberOptional = repository.findByPhoneNumber(entity.getPhoneNumber());
        if (contactPhoneNumberOptional.isEmpty()) {
            return repository.save(entity);
        } else {
            throw new IllegalArgumentException("Contact phone number is already created");
        }
    }

    @Override
    public ContactPhoneNumber update(ContactPhoneNumber entity) {
        Optional<ContactPhoneNumber> contactPhoneNumberOptional = repository.findById(entity.getId());
        if (contactPhoneNumberOptional.isPresent()) {

            ContactPhoneNumber target = contactPhoneNumberOptional.get();
            target.setContact(entity.getContact());
            target.setPhoneNumber(entity.getPhoneNumber());

            return repository.save(target);
        } else {
            throw new IllegalArgumentException("Contact phone number is already created");
        }
    }

    @Override
    public Optional<ContactPhoneNumber> getById(Long id) {
        return repository.findById(id);
    }

    @Override
    public String delete(Long entityId) {
        Optional<ContactPhoneNumber> contactPhoneNumberOptional = repository.findById(entityId);
        if (contactPhoneNumberOptional.isPresent()) {
            repository.delete(contactPhoneNumberOptional.get());
            return "Contact phone number with id + " + entityId + " deleted successfully";
        } else {
            throw new IllegalArgumentException("Unable to delete contact phone number");
        }
    }

    @Override
    public List<ContactPhoneNumber> findAll() {
        return repository.findAll();
    }
}
*/
