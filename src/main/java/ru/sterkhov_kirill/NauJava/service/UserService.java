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
        if (userRepository.findByUsername(user.username()).isPresent()) {
            throw new UsernameExists(user.username());
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(user.username());
        userEntity.setPasswordHash(passwordEncoder.encode(user.password()));
        userEntity.setEmail(user.email());

        userRepository.save(userEntity);
    }
}
