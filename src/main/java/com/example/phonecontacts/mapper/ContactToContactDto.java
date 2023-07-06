package com.example.phonecontacts.mapper;

import com.example.phonecontacts.dto.ContactDto;
import com.example.phonecontacts.entities.Contact;
import com.example.phonecontacts.entities.ContactEmail;
import com.example.phonecontacts.entities.ContactPhoneNumber;

import java.util.stream.Collectors;

public class ContactToContactDto {

    public static ContactDto contactToContactDto(Contact contact) {
        return ContactDto.builder()
                .name(contact.getName())
                .phones(contact.getPhones().stream()
                        .map(ContactPhoneNumber::getPhoneNumber)
                        .collect(Collectors.toSet()))
                .emails(contact.getEmails().stream()
                        .map(ContactEmail::getEmail)
                        .collect(Collectors.toSet()))
                .build();
    }
}
