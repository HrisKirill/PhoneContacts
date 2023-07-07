package com.example.phonecontacts.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response {
    private String message;
    private LocalTime time;
    private HttpStatus status;

}