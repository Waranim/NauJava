package ru.sterkhov_kirill.NauJava.repository;

import org.springframework.data.repository.CrudRepository;
import ru.sterkhov_kirill.NauJava.entity.UserEntity;

public interface UserRepository extends CrudRepository<UserEntity, Long> {
}
