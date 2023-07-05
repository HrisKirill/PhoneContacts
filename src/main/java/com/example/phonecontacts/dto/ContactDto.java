package com.example.phonecontacts.dto;

import lombok.Data;

import java.util.Set;

@Data
public class ContactDto {

    private String name;

    private Set<String> emails;

    private Set<String> phones;
}
