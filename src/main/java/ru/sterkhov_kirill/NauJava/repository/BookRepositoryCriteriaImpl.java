package ru.sterkhov_kirill.NauJava.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import ru.sterkhov_kirill.NauJava.entity.BookEntity;

import java.util.List;

@Repository
@RepositoryRestResource
@RequiredArgsConstructor
public class BookRepositoryCriteriaImpl implements BookRepositoryCriteria {

    private final EntityManager entityManager;

    @Override
    public List<BookEntity> findByGenreAndIsAvailableForOnline(String genre, boolean isAvailableForOnline) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<BookEntity> query = cb.createQuery(BookEntity.class);
        Root<BookEntity> book = query.from(BookEntity.class);

        Predicate genrePredicate = cb.equal(book.get("genre"), genre);
        Predicate onlinePredicate = cb.equal(book.get("isAvailableForOnline"), isAvailableForOnline);

        query.where(cb.and(genrePredicate, onlinePredicate));

        return entityManager.createQuery(query).getResultList();
    }
}
