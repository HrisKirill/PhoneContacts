package com.example.phonecontacts.mapper;

import com.example.phonecontacts.dto.ContactDto;
import com.example.phonecontacts.entities.Contact;
import com.example.phonecontacts.entities.ContactEmail;
import com.example.phonecontacts.entities.ContactPhoneNumber;
import com.example.phonecontacts.entities.User;

import java.util.HashSet;
import java.util.Set;

public class ContactDtoToContactMapper {

    public static Contact convertDtoContact(ContactDto dto, User user) {
        return Contact.builder()
                .user(user)
                .emails(convertDtoToContactEmail(dto.getEmails()))
                .phones(convertDtoToContactPhoneNumber(dto.getPhones()))
                .name(dto.getName())
                .build();
    }

    private static Set<ContactEmail> convertDtoToContactEmail(Set<String> emailDtos) {
        Set<ContactEmail> emails = new HashSet<>();
        for (String email :
                emailDtos) {
            emails.add(new ContactEmail(email));
        }
        return emails;
    }

    private static Set<ContactPhoneNumber> convertDtoToContactPhoneNumber(Set<String> numberDtos) {
        Set<ContactPhoneNumber> phoneNumbers = new HashSet<>();
        for (String phoneNumber :
                numberDtos) {
            phoneNumbers.add(new ContactPhoneNumber(phoneNumber));
        }
        return phoneNumbers;
    }
}
