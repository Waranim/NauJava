package ru.sterkhov_kirill.NauJava.dto;

import java.time.LocalDateTime;

public record ExceptionResponse(
        String message,
        String errorType,
        LocalDateTime date
) {
}
