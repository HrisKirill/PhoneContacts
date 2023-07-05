package com.example.phonecontacts.dao.services;

import com.example.phonecontacts.dao.interfaces.IContactDao;
import com.example.phonecontacts.dao.interfaces.IContactEmailDao;
import com.example.phonecontacts.dao.interfaces.IContactPhoneNumberDao;
import com.example.phonecontacts.dao.repositories.ContactRepository;
import com.example.phonecontacts.entities.Contact;
import com.example.phonecontacts.entities.ContactEmail;
import com.example.phonecontacts.entities.ContactPhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContactService implements IContactDao {

    private final IContactEmailDao contactEmailDao;
    private final IContactPhoneNumberDao contactPhoneNumberDao;
    private final ContactRepository contactRepository;


    @Autowired
    public ContactService(IContactEmailDao contactEmailDao,
                          IContactPhoneNumberDao contactPhoneNumberDao,
                          ContactRepository contactRepository) {
        this.contactEmailDao = contactEmailDao;
        this.contactPhoneNumberDao = contactPhoneNumberDao;
        this.contactRepository = contactRepository;
    }

    @Override
    public Contact create(Contact entity) {
        Optional<Contact> contactOptional = contactRepository.findByName(entity.getName());
        if (contactOptional.isEmpty()) {

            return contactRepository.save(entity);
        } else {
            throw new IllegalArgumentException("Contact is already created");
        }
    }

    @Override
    public Contact update(Contact entity) {
        Optional<Contact> contactOptional = contactRepository.findById(entity.getId());
        if (contactOptional.isPresent()) {

            Contact target = contactOptional.get();
            target.setUser(entity.getUser());
            target.setName(entity.getName());
            target.setEmails(entity.getEmails());
            target.setPhones(entity.getPhones());

            return contactRepository.save(target);
        } else {
            throw new IllegalArgumentException("Unable to update contact");
        }
    }

    @Override
    public Optional<Contact> getById(Long id) {
        return contactRepository.findById(id);
    }

    @Override
    public String delete(Long entityId) {
        Optional<Contact> contactOptional = contactRepository.findById(entityId);
        if (contactOptional.isPresent()) {
            contactRepository.delete(contactOptional.get());
            return "Contact with id + " + entityId + " deleted successfully";
        } else {
            throw new IllegalArgumentException("Unable to delete contact");
        }
    }

    @Override
    public List<Contact> findAll() {
        return contactRepository.findAll();
    }
}
