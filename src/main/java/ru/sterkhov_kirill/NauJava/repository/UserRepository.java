package ru.sterkhov_kirill.NauJava.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.sterkhov_kirill.NauJava.entity.UserEntity;

import java.util.Optional;

@RepositoryRestResource
public interface UserRepository extends CrudRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);
}
