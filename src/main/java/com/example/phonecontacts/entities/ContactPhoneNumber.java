package com.example.phonecontacts.entities;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class ContactPhoneNumber {
    @Pattern(regexp = "\\+380\\d{9}")
    private String phoneNumber;
}
