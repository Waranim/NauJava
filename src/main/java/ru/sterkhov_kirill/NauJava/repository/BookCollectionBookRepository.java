package ru.sterkhov_kirill.NauJava.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.sterkhov_kirill.NauJava.entity.BookCollectionBookEntity;

@RepositoryRestResource
public interface BookCollectionBookRepository extends CrudRepository<BookCollectionBookEntity, Long> {
}
