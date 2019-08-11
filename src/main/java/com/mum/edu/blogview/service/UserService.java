package com.edu.mum.service;

import com.edu.mum.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UserService {
//    Optional<User> findByEmailAndPassword(String email, String pass);
    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);
    User findById(Long id);
    User create(User user);
    Page<User> findAll(Pageable pageable);
    List<User> findAll();
    User edit(User user);
    void deleteById(Long id);
}
