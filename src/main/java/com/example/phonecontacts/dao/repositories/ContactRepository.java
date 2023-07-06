package com.example.phonecontacts.dao.repositories;

import com.example.phonecontacts.entities.Contact;
import com.example.phonecontacts.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
    Optional<Contact> findByName(String name);

    List<Contact> findAllByUser(User user);

    boolean existsContactByUser(User user);

}
