package ru.sterkhov_kirill.NauJava.repository;

import ru.sterkhov_kirill.NauJava.entity.UserCollectionEntity;

import java.util.List;

public interface UserCollectionRepositoryCriteria {
    List<UserCollectionEntity> findByUsername(String username);
}
