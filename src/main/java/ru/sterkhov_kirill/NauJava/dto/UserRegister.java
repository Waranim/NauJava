package ru.sterkhov_kirill.NauJava.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserRegister {
    private String username;
    private String password;
    private String email;
}
