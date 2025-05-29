package com.example.microfinancetracking.service;

import com.example.microfinancetracking.model.User;
import com.example.microfinancetracking.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUser(User user) {
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new IllegalArgumentException("Bu kullanıcı adı zaten mevcut.");
        }
        if (user.getEmail() != null && userRepository.findByEmail(user.getEmail()) != null) {
            throw new IllegalArgumentException("Bu e-posta zaten kayıtlı.");
        }

        return userRepository.save(user);
    }

    public User loginUser(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) { // Basit şifre kontrolü
            return user;
        }
        return null;
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }
}