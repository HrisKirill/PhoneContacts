package com.example.phonecontacts.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class ContactDto {

    private String name;

    private Set<String> emails;

    private Set<String> phones;
}
