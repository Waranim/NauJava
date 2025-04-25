package ru.sterkhov_kirill.NauJava.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserModelRegister {
    private String username;
    private String password;
    private String email;
}
