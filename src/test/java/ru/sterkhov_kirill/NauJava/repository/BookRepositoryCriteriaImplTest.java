package ru.sterkhov_kirill.NauJava.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.sterkhov_kirill.NauJava.entity.BookEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class BookRepositoryCriteriaImplTest {

    @Autowired
    private BookRepositoryCriteriaImpl criteriaRepository;

    @Autowired
    private BookRepository bookRepository;

    @Test
    void findByGenreAndAvailableForOnline_ShouldUseCriteria() {
        String genre = "Романтика";
        BookEntity book1 = createTestBook(genre);
        BookEntity book2 = createTestBook("Детектив");
        bookRepository.saveAll(List.of(book1, book2));

        List<BookEntity> result = criteriaRepository.findByGenreAndIsAvailableForOnline(genre, true);

        assertEquals(1, result.size());
        assertEquals(genre, result.getFirst().getGenre());
    }

    private BookEntity createTestBook(String genre) {
        BookEntity book = new BookEntity();
        book.setTitle("Тестовая книга");
        book.setAuthor("Автор");
        book.setGenre(genre);
        book.setAvailableForOnline(true);
        book.setAvailableCopies(5);
        return book;
    }
}
