package com.example.phonecontacts.controller;

import com.example.phonecontacts.authorization.interfaces.IAuth;
import com.example.phonecontacts.dao.interfaces.IUserDao;
import com.example.phonecontacts.dto.LoginDto;
import com.example.phonecontacts.dto.SignUpDto;
import com.example.phonecontacts.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
