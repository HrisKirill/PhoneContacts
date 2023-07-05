package com.example.phonecontacts.security;


import com.example.phonecontacts.dao.interfaces.IUserDao;
import com.example.phonecontacts.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final IUserDao dao;

    @Autowired
    public CustomUserDetailsService(IUserDao dao) {
        this.dao = dao;
    }

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        User user = dao.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with username or email: "+ usernameOrEmail));

        return new org.springframework.security.core.userdetails.User(user.getEmail(),
                user.getPassword(),
                new HashSet<>());
    }
}
