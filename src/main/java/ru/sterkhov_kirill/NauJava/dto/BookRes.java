package ru.sterkhov_kirill.NauJava.dto;

import ru.sterkhov_kirill.NauJava.entity.BookEntity;

import java.util.List;

public record BookRes(
        Long id,
        String title,
        String author,
        String isbn,
        String genre,
        Integer totalCopies,
        Integer availableCopies,
        Boolean isAvailableForOnline
) {
    public static BookRes fromEntity(BookEntity book) {
        return new BookRes(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getIsbn(),
                book.getGenre(),
                book.getTotalCopies(),
                book.getAvailableCopies(),
                book.isAvailableForOnline()
        );
    }

    public static List<BookRes> fromEntities(List<BookEntity> books) {
        return books.stream().map(BookRes::fromEntity).toList();
    }
}
