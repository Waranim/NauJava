package ru.sterkhov_kirill.NauJava.repository;

import org.springframework.data.repository.CrudRepository;
import ru.sterkhov_kirill.NauJava.entity.BookCollectionBookEntity;

public interface BookCollectionBookRepository extends CrudRepository<BookCollectionBookEntity, Long> {
}
