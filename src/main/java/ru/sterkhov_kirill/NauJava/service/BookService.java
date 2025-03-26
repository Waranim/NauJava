package ru.sterkhov_kirill.NauJava.service;

import ru.sterkhov_kirill.NauJava.entity.Book;

public interface BookService {

    Boolean createBook(Long id, String title, String author);

    Book findBookById(Long id);

    void updateTitle(Long id, String newTitle);

    void updateAuthor(Long id, String newAuthor);

    Boolean deleteBookById(Long id);
}
