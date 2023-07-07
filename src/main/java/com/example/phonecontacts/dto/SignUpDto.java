package com.example.phonecontacts.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SignUpDto {

    private String name;
    private String username;
    private String email;
    private String password;
}
