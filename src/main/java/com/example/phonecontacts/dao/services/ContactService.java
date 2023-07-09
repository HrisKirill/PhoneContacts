package com.example.phonecontacts.dao.services;

import com.example.phonecontacts.dao.interfaces.IContactDao;
import com.example.phonecontacts.dao.interfaces.IUserDao;
import com.example.phonecontacts.dao.repositories.ContactRepository;
import com.example.phonecontacts.entities.Contact;
import com.example.phonecontacts.entities.ContactEmail;
import com.example.phonecontacts.entities.ContactPhoneNumber;
import com.example.phonecontacts.exceptions.DuplicateException;
import com.example.phonecontacts.exceptions.IllegalImageFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContactService implements IContactDao {
    private final ContactRepository contactRepository;

    private final IUserDao userDao;


    @Autowired
    public ContactService(ContactRepository contactRepository, IUserDao userDao) {
        this.contactRepository = contactRepository;
        this.userDao = userDao;
    }

    @Override
    public Contact create(Contact entity) {
        Optional<Contact> contactOptional = contactRepository.findByName(entity.getName());
        List<Contact> contacts = findAll();

        checkContactForValidity(contacts, entity);
        if (contactOptional.isEmpty()) {
            return contactRepository.save(entity);
        } else {
            throw new IllegalArgumentException("Contact is already created");
        }
    }


    @Override
    public Contact update(Contact entity, Long entityID) {
        Optional<Contact> contactOptional = getById(entityID);
        if (contactOptional.isPresent() &&
                contactRepository.existsContactByUser(userDao.getCurrentUser())) {
            List<Contact> contacts = findAll();
            entity.setId(entityID);
            checkContactForValidity(contacts, entity);

            Contact target = contactOptional.get();
            target.setUser(entity.getUser());
            target.setName(entity.getName());
            target.setImage(entity.getImage());
            target.setEmails(entity.getEmails());
            target.setPhones(entity.getPhones());

            return contactRepository.save(target);
        } else {
            throw new IllegalArgumentException("Unable to update contact");
        }
    }

    @Override
    public Optional<Contact> getById(Long id) {
        List<Contact> contacts = findAll();
        int resultId = Math.toIntExact(id) - 1;
        if (!contacts.isEmpty() && resultId >= 0 && resultId < contacts.size()) {
            return Optional.of(contacts.get(resultId));
        }

        return Optional.empty();
    }

    @Override
    public String delete(Long entityId) {
        List<Contact> contacts = findAll();
        int id = Math.toIntExact(entityId) - 1;
        if (!contacts.isEmpty() && id >= 0 && id < contacts.size()) {
            contactRepository.delete(contacts.get(id));
            return "Contact with id " + entityId + " deleted successfully";
        } else {
            throw new IllegalArgumentException("Unable to delete contact");
        }
    }

    @Override
    public List<Contact> findAll() {
        return contactRepository.findAllByUser(userDao.getCurrentUser());
    }

    private boolean areEmailDuplicates(List<Contact> userContacts, Contact target) {
        for (Contact contact :
                userContacts) {
            for (ContactEmail email :
                    target.getEmails()) {
                if (contact.getEmails().contains(email) &&
                        !contact.getId().equals(target.getId())) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean arePhoneNumbersDuplicates(List<Contact> userContacts, Contact target) {
        for (Contact contact :
                userContacts) {
            for (ContactPhoneNumber phoneNumber :
                    target.getPhones()) {
                if (contact.getPhones().contains(phoneNumber) &&
                        !contact.getId().equals(target.getId())) {
                    return true;
                }
            }
        }

        return false;
    }


    private void checkContactForValidity(List<Contact> contacts, Contact entity) {

        if (areEmailDuplicates(contacts, entity)) {
            throw new DuplicateException("Duplicate email");
        }

        if (arePhoneNumbersDuplicates(contacts, entity)) {
            throw new DuplicateException("Duplicate phone number");
        }

        if (entity.getImage() != null && !isImageFormat(entity.getImage().getName())) {
            throw new IllegalImageFormatException("Incorrect image format");
        }
    }

    private boolean isImageFormat(String fileName) {
        String[] availableFormats = {"png", "jpg", "jpeg"};
        for (String format :
                availableFormats) {
            if (fileName.endsWith(format)) {
                return true;
            }
        }

        return false;
    }
}
