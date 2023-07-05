package com.example.phonecontacts.dto;

import lombok.Data;

import java.util.Set;

@Data
public class ContactDto {

    private String name;

    private Set<ContactEmailDto> emails;

    private Set<ContactPhoneNumberDto> phones;
}
