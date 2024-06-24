package com.br.matheus.eccomerce.services.impl;

import com.br.matheus.eccomerce.models.User;
import com.br.matheus.eccomerce.repositories.UserRepository;
import com.br.matheus.eccomerce.services.UserService;
import com.br.matheus.eccomerce.services.exceptions.IntegrityViolation;
import com.br.matheus.eccomerce.services.exceptions.ObjectNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    private void validateEmail(User user) {
        ifEmailExistThrowsIntegrityViolationException(user);
    }

    private void ifEmailExistThrowsIntegrityViolationException(User user) {
        Optional<User> newUser = repository.findByEmail(user.getEmail());
        if(newUser.isPresent() && newUser.get().getId() != user.getId()) {
            throw new IntegrityViolation("This email already exists");
        }
    }


    @Override
    public Page<User> findAll(Pageable pageable) {
        Page<User> users = repository.findAll(pageable);
        if(users.isEmpty()) {
            throw new ObjectNotFound("Did't find any users");
        }
        return users;
    }

    @Override
    public User findById(Long id) {
        Optional<User> user = repository.findById(id);
        return user.orElseThrow(() -> new ObjectNotFound("Did't find any user with id: " + id));
    }

    @Override
    public User insert(User user) {
        validateEmail(user);
        return repository.save(user);
    }

    @Override
    public User update(User user) {
        validateEmail(user);
        return repository.save(findById(user.getId()));
    }

    @Override
    public void delete(Long id) {
        repository.delete(findById(id));
    }

    @Override
    public User findByEmail(String email) {
        Optional<User> user = repository.findByEmail(email);
        return user.orElseThrow(() -> new ObjectNotFound("Did't find any user with email: " + email));
    }
}
