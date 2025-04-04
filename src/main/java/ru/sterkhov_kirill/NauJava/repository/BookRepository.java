package ru.sterkhov_kirill.NauJava.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.sterkhov_kirill.NauJava.entity.BookEntity;

import java.util.List;

@RepositoryRestResource
public interface BookRepository extends CrudRepository<BookEntity, Long> {
    List<BookEntity> findByGenreAndIsAvailableForOnline(String genre, boolean isAvailableForOnline);
}
