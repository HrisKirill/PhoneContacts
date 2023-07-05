package com.example.phonecontacts.mapper;

import com.example.phonecontacts.dto.ContactDto;
import com.example.phonecontacts.dto.ContactEmailDto;
import com.example.phonecontacts.dto.ContactPhoneNumberDto;
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

    private static Set<ContactEmail> convertDtoToContactEmail(Set<ContactEmailDto> emailDtos) {
        Set<ContactEmail> emails = new HashSet<>();
        for (ContactEmailDto emailDto :
                emailDtos) {
            emails.add(ContactEmail.builder()
                    .email(emailDto.getEmail())
                    .build());
        }
        System.out.println("emails: " + emails);
        return emails;
    }

    private static Set<ContactPhoneNumber> convertDtoToContactPhoneNumber(Set<ContactPhoneNumberDto> numberDtos) {
        Set<ContactPhoneNumber> phoneNumbers = new HashSet<>();
        for (ContactPhoneNumberDto numberDto :
                numberDtos) {
            phoneNumbers.add(ContactPhoneNumber.builder()
                    .phoneNumber(numberDto.getPhoneNumber())
                    .build());
        }
        System.out.println("phoneNumbers: " + phoneNumbers);
        return phoneNumbers;
    }
}
