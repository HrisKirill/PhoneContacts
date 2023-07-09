package com.example.phonecontacts.mapper;

import com.example.phonecontacts.dto.ContactDto;
import com.example.phonecontacts.entities.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class ContactDtoToContactMapper {

    public static Contact convertDtoContact(ContactDto dto, User user, MultipartFile file) {
        Contact contact = Contact.builder()
                .user(user)
                .emails(convertDtoToContactEmail(dto.getEmails()))
                .phones(convertDtoToContactPhoneNumber(dto.getPhones()))
                .name(dto.getName())
                .build();

        if (file != null && !file.isEmpty()) {
            try {
                contact.setImage(Image.builder()
                        .name(file.getOriginalFilename())
                        .imageBytes(file.getBytes())
                        .build());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return contact;
    }

    private static Set<ContactEmail> convertDtoToContactEmail(Set<String> emailDtos) {
        Set<ContactEmail> emails = new HashSet<>();
        if (emailDtos != null && !emailDtos.isEmpty()) {
            for (String email :
                    emailDtos) {
                emails.add(new ContactEmail(email));
            }
            return emails;
        }
        return Collections.emptySet();
    }

    private static Set<ContactPhoneNumber> convertDtoToContactPhoneNumber(Set<String> numberDtos) {
        Set<ContactPhoneNumber> phoneNumbers = new HashSet<>();
        if (numberDtos != null && !numberDtos.isEmpty()) {
            for (String phoneNumber :
                    numberDtos) {
                phoneNumbers.add(new ContactPhoneNumber(phoneNumber));
            }

            return phoneNumbers;
        }

        return Collections.emptySet();
    }
}
