package ru.sterkhov_kirill.NauJava.exception;

public class UsernameExists extends RuntimeException {
    public UsernameExists(String message) {
        super("Уже существует пользователь с именем: " + message);
    }
}
