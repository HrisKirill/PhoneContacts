package com.example.phonecontacts.authorization.services;

import com.example.phonecontacts.authorization.interfaces.IAuth;
import com.example.phonecontacts.dao.interfaces.IUserDao;
import com.example.phonecontacts.dto.LoginDto;
import com.example.phonecontacts.dto.SignUpDto;
import com.example.phonecontacts.entities.User;
import com.example.phonecontacts.exceptions.APIException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements IAuth {

    private final AuthenticationManager authenticationManager;

    private final IUserDao iUserDao;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthService(AuthenticationManager authenticationManager, IUserDao iUserDao, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.iUserDao = iUserDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String login(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getLogin(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return "User signed-in successfully!";
    }

    @Override
    public String register(SignUpDto signUpDto) {
        if (iUserDao.existsByUsername(signUpDto.getUsername())) {
            throw new APIException(HttpStatus.BAD_REQUEST, "Username is already taken!");
        }

        if (iUserDao.existsByEmail(signUpDto.getEmail())) {
            throw new APIException(HttpStatus.BAD_REQUEST, "Email is already taken!");
        }

        User user = User.builder()
                .name(signUpDto.getName())
                .userName(signUpDto.getUsername())
                .email(signUpDto.getEmail())
                .password(passwordEncoder.encode(signUpDto.getPassword()))
                .build();

        iUserDao.create(user);
        return "User registered successfully!";
    }
}
