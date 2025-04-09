package ru.sterkhov_kirill.NauJava.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.sterkhov_kirill.NauJava.dto.UserRegister;
import ru.sterkhov_kirill.NauJava.entity.UserEntity;
import ru.sterkhov_kirill.NauJava.exception.UsernameExists;
import ru.sterkhov_kirill.NauJava.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void createUser(UserRegister user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new UsernameExists(user.getUsername());
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(user.getUsername());
        userEntity.setPasswordHash(passwordEncoder.encode(user.getPassword()));
        userEntity.setEmail(user.getEmail());

        userRepository.save(userEntity);
    }
}
