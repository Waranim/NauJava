package ru.sterkhov_kirill.NauJava.repository;

import ru.sterkhov_kirill.NauJava.entity.BookEntity;

import java.util.List;

public interface BookRepositoryCriteria {
    List<BookEntity> findByGenreAndIsAvailableForOnline(String genre, boolean isAvailableForOnline);
}
