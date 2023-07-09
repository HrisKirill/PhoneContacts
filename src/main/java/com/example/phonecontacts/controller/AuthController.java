package com.example.phonecontacts.controller;

import com.example.phonecontacts.authorization.interfaces.IAuth;
import com.example.phonecontacts.dto.LoginDto;
import com.example.phonecontacts.dto.SignUpDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {


    private final IAuth auth;

    @Autowired
    public AuthController(IAuth auth) {
        this.auth = auth;
    }

    @PostMapping({"/signin", "/login"})
    public ResponseEntity<String> authenticateUser(@RequestBody LoginDto loginDto) {
        return new ResponseEntity<>(auth.login(loginDto), HttpStatus.OK);
    }

    @PostMapping({"/signup", "/register"})
    public ResponseEntity<String> registerUser(@RequestBody SignUpDto signUpDto) {
        return new ResponseEntity<>(auth.register(signUpDto), HttpStatus.OK);

    }

    @PostMapping(value = "/confirm-account")
    public ResponseEntity<String> confirmUserAccount(@RequestParam("token") String confirmationToken) {
        return new ResponseEntity<>(auth.confirmEmail(confirmationToken), HttpStatus.OK);
    }
}
