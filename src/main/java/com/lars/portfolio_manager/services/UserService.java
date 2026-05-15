package com.lars.portfolio_manager.services;

import com.lars.portfolio_manager.dto.RegisterForm;
import com.lars.portfolio_manager.entities.CustomUser;
import com.lars.portfolio_manager.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void register(RegisterForm form) {
        CustomUser user = new CustomUser(
                form.username(),
                form.email(),
                passwordEncoder.encode(form.password())
        );

        userRepository.save(user);
    }

    public boolean usernameExists(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }
}
