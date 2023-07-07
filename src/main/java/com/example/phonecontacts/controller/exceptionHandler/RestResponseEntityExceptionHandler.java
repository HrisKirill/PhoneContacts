package com.example.phonecontacts.controller.exceptionHandler;

import com.example.phonecontacts.entities.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalTime;

@ControllerAdvice(annotations = Controller.class)
public class RestResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response> handleException(Exception e) {
        Response response = new Response(e.getMessage(), LocalTime.now(), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(response, response.getStatus());
    }
}
