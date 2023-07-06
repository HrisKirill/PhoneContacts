package com.example.phonecontacts.exceptions;

import lombok.Getter;

@Getter
public class DuplicateException extends RuntimeException {

    private final String message;

    public DuplicateException(String message) {
        this.message = message;
    }

}
