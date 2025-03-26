package ru.sterkhov_kirill.NauJava.service;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import ru.sterkhov_kirill.NauJava.entity.Book;
import ru.sterkhov_kirill.NauJava.repository.BookRepository;

@Service
@Scope(value = BeanDefinition.SCOPE_SINGLETON)
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Boolean createBook(Long id, String title, String author) {
        Book book = new Book(id, title, author);
        return bookRepository.create(book);
    }

    @Override
    public Book findBookById(Long id) {
        return bookRepository.read(id);
    }

    @Override
    public void updateTitle(Long id, String newTitle) {
        Book book = findBookById(id);
        if (book != null) {
            book.setTitle(newTitle);
            bookRepository.update(book);
        }
    }

    @Override
    public void updateAuthor(Long id, String newAuthor) {
        Book book = findBookById(id);
        if (book != null) {
            book.setAuthor(newAuthor);
            bookRepository.update(book);
        }
    }

    @Override
    public Boolean deleteBookById(Long id) {
        return bookRepository.delete(id);
    }
}
