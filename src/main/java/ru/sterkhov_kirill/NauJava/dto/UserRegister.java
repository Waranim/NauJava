package ru.sterkhov_kirill.NauJava.dto;

public record UserRegister(
        String username,
        String password,
        String email
) {
}
