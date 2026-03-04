package com.example.btcsedge.security;

import com.example.btcsedge.domain.model.User;
import com.example.btcsedge.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Authenticate a user by email and raw password.
     *
     * @param email    the user's email address
     * @param password the raw (plain-text) password to verify
     * @return an Optional containing the authenticated User, or empty if authentication fails
     */
    public Optional<User> authenticate(String email, String password) {
        return userRepository.findByEmail(email)
                .filter(user -> passwordEncoder.matches(password, user.getPassword()))
                .filter(User::isActive);
    }
}
