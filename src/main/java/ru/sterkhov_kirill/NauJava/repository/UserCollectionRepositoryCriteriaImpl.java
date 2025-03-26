package ru.sterkhov_kirill.NauJava.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.sterkhov_kirill.NauJava.entity.UserCollectionEntity;
import ru.sterkhov_kirill.NauJava.entity.UserEntity;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserCollectionRepositoryCriteriaImpl implements UserCollectionRepositoryCriteria {

    private final EntityManager entityManager;

    @Override
    public List<UserCollectionEntity> findByUsername(String username) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<UserCollectionEntity> query = cb.createQuery(UserCollectionEntity.class);
        Root<UserCollectionEntity> collection = query.from(UserCollectionEntity.class);

        Join<UserCollectionEntity, UserEntity> userJoin = collection.join("user");

        Predicate usernamePredicate = cb.equal(userJoin.get("username"), username);

        query.where(usernamePredicate);

        return entityManager.createQuery(query).getResultList();
    }
}
