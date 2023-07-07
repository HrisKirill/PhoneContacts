package com.example.phonecontacts.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContactDto {

    private String name;

    private Set<String> emails;

    private Set<String> phones;
}
