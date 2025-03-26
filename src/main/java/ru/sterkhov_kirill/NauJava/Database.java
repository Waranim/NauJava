package ru.sterkhov_kirill.NauJava;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.sterkhov_kirill.NauJava.entity.Book;

import java.util.ArrayList;
import java.util.List;

@Component
@Scope(value = BeanDefinition.SCOPE_SINGLETON)
public class Database {

    private final List<Book> container;

    public Database() {
        this.container = new ArrayList<>();
    }

    public Book getBook(long id) {
        return container.stream().filter(b -> b.getId() == id).findFirst().orElse(null);
    }

    public boolean addBook(Book book) {
        return container.add(book);
    }

    public void updateBook(long id, Book book) {
        container.stream().filter(b -> b.getId() == id).findFirst().ifPresent(b -> {
            b.setTitle(book.getTitle());
            b.setAuthor(book.getAuthor());
        });
    }

    public boolean removeBook(long id) {
        return container.removeIf(b -> b.getId() == id);
    }
}
