package ru.sterkhov_kirill.NauJava.service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.sterkhov_kirill.NauJava.entity.BookEntity;
import ru.sterkhov_kirill.NauJava.entity.ReservationEntity;
import ru.sterkhov_kirill.NauJava.entity.UserEntity;
import ru.sterkhov_kirill.NauJava.enums.ReservationStatus;
import ru.sterkhov_kirill.NauJava.repository.BookRepository;
import ru.sterkhov_kirill.NauJava.repository.ReservationRepository;
import ru.sterkhov_kirill.NauJava.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ReservationRepository reservationRepository;

    @InjectMocks
    private ReservationService reservationService;

    private BookEntity book;
    private UserEntity user;

    @BeforeEach
    void setUp() {
        book = new BookEntity();
        book.setId(1L);
        book.setTitle("Mockito in Action");
        book.setAvailableCopies(5);

        user = new UserEntity();
        user.setId(10L);
        user.setUsername("john_doe");
    }

    @Test
    void createReservationWithSuccess() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(userRepository.findById(10L)).thenReturn(Optional.of(user));

        ArgumentCaptor<ReservationEntity> captor = ArgumentCaptor.forClass(ReservationEntity.class);
        when(reservationRepository.save(captor.capture())).thenAnswer(invocation -> {
            ReservationEntity r = invocation.getArgument(0);
            r.setId(100L);
            return r;
        });


        ReservationEntity result = reservationService.createReservation(1L, 10L, 7);


        assertNotNull(result.getId(), "Должен быть установлен идентификатор");
        assertEquals(book, result.getBook());
        assertEquals(user, result.getUser());
        assertEquals(ReservationStatus.ACTIVE, result.getStatus());

        LocalDateTime now = LocalDateTime.now();
        assertFalse(result.getStartDate().isAfter(now), "startDate не позже текущего времени");
        assertTrue(result.getEndDate().isAfter(result.getStartDate()), "endDate позже startDate");

        assertEquals(4, book.getAvailableCopies());
        verify(bookRepository).save(book);
        verify(reservationRepository).save(any(ReservationEntity.class));
    }

    @Test
    void createReservation_bookNotFound() {
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        EntityNotFoundException ex = assertThrows(
                EntityNotFoundException.class,
                () -> reservationService.createReservation(1L, 10L, 5)
        );
        assertEquals("Книга не найдена", ex.getMessage());
        verify(bookRepository).findById(1L);
        verifyNoMoreInteractions(bookRepository, userRepository, reservationRepository);
    }

    @Test
    void createReservation_userNotFound() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(userRepository.findById(10L)).thenReturn(Optional.empty());

        EntityNotFoundException ex = assertThrows(
                EntityNotFoundException.class,
                () -> reservationService.createReservation(1L, 10L, 5)
        );
        assertEquals("Пользователь не найден", ex.getMessage());
        verify(bookRepository).findById(1L);
        verify(userRepository).findById(10L);
        verifyNoMoreInteractions(bookRepository, userRepository, reservationRepository);
    }

    @Test
    void createReservation_noAvailableCopies() {
        book.setAvailableCopies(0);
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(userRepository.findById(10L)).thenReturn(Optional.of(user));

        IllegalStateException ex = assertThrows(
                IllegalStateException.class,
                () -> reservationService.createReservation(1L, 10L, 3)
        );
        assertEquals("Книга недоступна для бронирования", ex.getMessage());

        verify(bookRepository).findById(1L);
        verify(userRepository).findById(10L);
        verify(bookRepository, never()).save(any());
        verify(reservationRepository, never()).save(any());
    }
}
