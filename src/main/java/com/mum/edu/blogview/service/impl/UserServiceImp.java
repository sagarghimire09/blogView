package com.mum.edu.blogview.service.impl;

import com.mum.edu.blogview.domain.User;
import com.mum.edu.blogview.repository.RoleRepository;
import com.mum.edu.blogview.repository.UserRepository;
import com.mum.edu.blogview.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
//    private final PasswordEncoder passwordEncoder;

    private static final String USER_ROLE = "ROLE_USER";
    private static final String ADMIN_ROLE = "ROLE_ADMIN";

    @Autowired
    public UserServiceImp(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
//        this.passwordEncoder = passwordEncoder;
    }

    public Optional<User> findByUsernameAndPassword(String username, String pass) {
        return userRepository.findByUsernameAndPassword(username, pass);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User findById(Long id) {
        return this.userRepository.getOne(id);
    }

    @Override
    public List<User> findAll() {
        return this.userRepository.findAll();
    }
    @Override
    public User create(User user) {
        // Encode plaintext password
        user.setPassword(user.getPassword());
        user.setActive(1);
        // Set Role to ROLE_USER/ROLE_ADMIN
        int count = userRepository.findAll().size();
        if (count < 1){
            user.setRoles(roleRepository.findByRole(ADMIN_ROLE));
        }else {
            user.setRoles(roleRepository.findByRole(USER_ROLE));
        }

        return userRepository.save(user);
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        return this.userRepository.findAll(pageable);
    }

    @Override
    public User edit(User user) {
        return this.userRepository.save(user);
    }
    @Override
    public void deleteById(Long id) {
        this.userRepository.deleteById(id);
    }
}
