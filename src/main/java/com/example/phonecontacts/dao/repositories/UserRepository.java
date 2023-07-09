package com.example.phonecontacts.dao.repositories;

import com.example.phonecontacts.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByUserNameOrEmail(String userName, String email);

    Optional<User> findUserByEmail(String email);

    Boolean existsByUserName(String username);

    Boolean existsByEmail(String email);
}
