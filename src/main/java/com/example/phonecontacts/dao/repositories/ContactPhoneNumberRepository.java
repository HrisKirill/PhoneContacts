package com.example.phonecontacts.dao.repositories;

import com.example.phonecontacts.entities.ContactPhoneNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactPhoneNumberRepository extends JpaRepository<ContactPhoneNumber, Long> {
}
