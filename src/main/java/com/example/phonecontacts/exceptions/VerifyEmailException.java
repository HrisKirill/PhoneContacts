package com.example.phonecontacts.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class VerifyEmailException extends RuntimeException {
    public VerifyEmailException(String message) {
        super(message);
    }
}
