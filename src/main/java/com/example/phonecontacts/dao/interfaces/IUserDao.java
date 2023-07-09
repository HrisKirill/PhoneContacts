package com.example.phonecontacts.dao.interfaces;

import com.example.phonecontacts.entities.User;

import java.util.Optional;

public interface IUserDao extends Dao<User> {
    Optional<User> findByUsernameOrEmail(String username, String email);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    Optional<User> findUserByEmail(String email);

    User getCurrentUser();
}
