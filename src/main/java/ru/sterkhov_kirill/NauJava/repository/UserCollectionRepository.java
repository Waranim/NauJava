package ru.sterkhov_kirill.NauJava.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.sterkhov_kirill.NauJava.entity.UserCollectionEntity;

import java.util.List;

public interface UserCollectionRepository extends CrudRepository<UserCollectionEntity, Long> {
    @Query("SELECT uc FROM UserCollectionEntity uc JOIN uc.user u WHERE u.username = :username")
    List<UserCollectionEntity> findByUsername(@Param("username") String username);
}
