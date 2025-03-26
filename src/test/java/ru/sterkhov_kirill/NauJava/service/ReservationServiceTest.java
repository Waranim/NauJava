package ru.sterkhov_kirill.NauJava.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.sterkhov_kirill.NauJava.entity.BookEntity;
import ru.sterkhov_kirill.NauJava.entity.ReservationEntity;
import ru.sterkhov_kirill.NauJava.entity.UserEntity;
import ru.sterkhov_kirill.NauJava.repository.BookRepository;
import ru.sterkhov_kirill.NauJava.repository.ReservationRepository;
import ru.sterkhov_kirill.NauJava.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReservationServiceTest {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Test
    void createReservation_ShouldDecreaseAvailableCopies() {
        BookEntity book = createTestBook(3);
        UserEntity user = createTestUser(123);
        bookRepository.save(book);
        userRepository.save(user);

        ReservationEntity reservation = reservationService.createReservation(
                book.getId(),
                user.getId(),
                3
        );

        BookEntity updatedBook = bookRepository.findById(book.getId()).orElseThrow();
        assertEquals(2, updatedBook.getAvailableCopies());
        assertNotNull(reservationRepository.findById(reservation.getId()));
    }

    @Test
    void createReservation_ShouldRollbackOnError() {
        BookEntity book = createTestBook(0);
        UserEntity user = createTestUser(234);
        bookRepository.save(book);
        userRepository.save(user);

        IllegalStateException ex = assertThrows(IllegalStateException.class, () ->
            reservationService.createReservation(book.getId(), user.getId(), 3)
        );

        BookEntity updatedBook = bookRepository.findById(book.getId()).orElseThrow();
        assertEquals(0, updatedBook.getAvailableCopies());
        assertEquals(0, reservationRepository.count());
        assertEquals("Книга недоступна для бронирования", ex.getMessage());
    }


    private BookEntity createTestBook(int availableCopies) {
        BookEntity book = new BookEntity();
        book.setTitle("Тестовая книга");
        book.setAuthor("Автор");
        book.setAvailableCopies(availableCopies);
        return book;
    }

    private UserEntity createTestUser(Integer numberUser) {
        UserEntity user = new UserEntity();
        user.setUsername("test_user_" + numberUser);
        user.setPasswordHash("ejlkjfesjfef1482fsdf");
        user.setEmail(numberUser + "@example.com");
        return user;
    }
}
