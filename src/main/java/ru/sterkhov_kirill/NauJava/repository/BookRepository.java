package ru.sterkhov_kirill.NauJava.repository;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.sterkhov_kirill.NauJava.Database;
import ru.sterkhov_kirill.NauJava.entity.Book;

@Component
@Scope(value = BeanDefinition.SCOPE_SINGLETON)
public class BookRepository implements CrudRepository<Book, Long> {

    private final Database container;

    public BookRepository(Database container) {
        this.container = container;
    }

    @Override
    public boolean create(Book book) {
        return container.addBook(book);
    }

    @Override
    public Book read(Long id) {
        return container.getBook(id);
    }

    @Override
    public void update(Book book) {
        container.updateBook(book.getId(), book);
    }

    @Override
    public boolean delete(Long id) {
        return container.removeBook(id);
    }
}
