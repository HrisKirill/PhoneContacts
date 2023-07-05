package com.example.phonecontacts.authorization.interfaces;

import com.example.phonecontacts.dto.LoginDto;
import com.example.phonecontacts.dto.SignUpDto;

public interface IAuth {

    String login(LoginDto loginDto);

    String register(SignUpDto registerDto);
}
