package ru.sterkhov_kirill.NauJava.repository;

import org.springframework.data.repository.CrudRepository;
import ru.sterkhov_kirill.NauJava.entity.BookCollectionEntity;

public interface BookCollectionRepository extends CrudRepository<BookCollectionEntity, Long> {
}
