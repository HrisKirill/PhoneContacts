package com.example.phonecontacts.dao.services;

import com.example.phonecontacts.dao.interfaces.IUserDao;
import com.example.phonecontacts.dao.repositories.UserRepository;
import com.example.phonecontacts.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserDao {
    private final UserRepository repository;

    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public User create(User entity) {
        Optional<User> optionalUser = repository.findUserByUserNameOrEmail(entity.getUserName(),entity.getEmail());
        if (optionalUser.isEmpty()) {
            return repository.save(entity);
        } else {
            throw new IllegalArgumentException("User is already created");
        }
    }

    @Override
    public User update(User entity) {
        Optional<User> optionalUser = repository.findById(entity.getId());
        if (optionalUser.isPresent()) {

            User target = optionalUser.get();
            target.setName(entity.getName());
            target.setUserName(entity.getUserName());
            target.setEmail(entity.getEmail());
            target.setPassword(entity.getPassword());
            target.setContacts(entity.getContacts());

            return repository.save(target);
        } else {
            throw new IllegalArgumentException("Unable to update user");
        }

    }

    @Override
    public Optional<User> getById(Long id) {
        return repository.findById(id);
    }

    @Override
    public String delete(Long entityId) {
        Optional<User> optionalUser = repository.findById(entityId);
        if (optionalUser.isPresent()) {
            repository.delete(optionalUser.get());
            return "User with id + " + entityId + " deleted successfully";
        } else {
            throw new IllegalArgumentException("Unable to delete event");
        }
    }

    @Override
    public List<User> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<User> findByUsernameOrEmail(String username, String email) {
        return repository.findUserByUserNameOrEmail(username, email);
    }

    @Override
    public Boolean existsByUsername(String username) {
        return repository.existsByUserName(username);
    }

    @Override
    public Boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }
}
