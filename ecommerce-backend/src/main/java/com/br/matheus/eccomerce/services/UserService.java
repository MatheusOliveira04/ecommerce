package com.br.matheus.eccomerce.services;

import com.br.matheus.eccomerce.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {

    Page<User> findAll(Pageable pageable);

    User findById(Long id);

    User insert(User user);

    User update(User user);

    void delete(Long id);

    User findByEmail(String email);
}
