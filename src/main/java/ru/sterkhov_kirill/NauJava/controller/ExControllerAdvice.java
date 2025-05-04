package ru.sterkhov_kirill.NauJava.controller;

import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;
import ru.sterkhov_kirill.NauJava.dto.ExceptionResponse;

import java.time.LocalDateTime;

@ControllerAdvice
public class ExControllerAdvice {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponse exception(ResourceNotFoundException e)
    {
        return new ExceptionResponse(
                "Запрашиваемый элемент не найден: " + e.getMessage(),
                e.getClass().getSimpleName(),
                LocalDateTime.now()
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse exception(MethodArgumentNotValidException e)
    {
        return new ExceptionResponse(
                "Валидация не пройдена: " + e.getMessage(),
                e.getClass().getSimpleName(),
                LocalDateTime.now()
        );
    }

    @ExceptionHandler(HttpClientErrorException.Conflict.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.CONFLICT)
    public ExceptionResponse exception(HttpClientErrorException.Conflict e)
    {
        return new ExceptionResponse(
                "Произошёл конфликт: " + e.getMessage(),
                e.getClass().getSimpleName(),
                LocalDateTime.now()
        );
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse exception(Exception e)
    {
        return new ExceptionResponse(
                "На стороне сервера произошла ошибка: " + e.getMessage(),
                e.getClass().getSimpleName(),
                LocalDateTime.now()
        );
    }
}
