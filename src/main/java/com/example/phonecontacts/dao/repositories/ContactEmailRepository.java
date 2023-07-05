package com.example.phonecontacts.dao.repositories;

import com.example.phonecontacts.entities.ContactEmail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContactEmailRepository extends JpaRepository<ContactEmail, Long> {
    Optional<ContactEmail> findByEmail(String email);
}
