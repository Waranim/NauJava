package ru.sterkhov_kirill.NauJava.dto;

import ru.sterkhov_kirill.NauJava.entity.UserCollectionEntity;

import java.util.List;

public record UserCollectionsRes(
        Long id,
        String username,
        String name,
        Boolean isReadyForReservation,
        Boolean isCompleted,
        List<BookRes> books
) {
    public static UserCollectionsRes fromEntity(UserCollectionEntity collection) {
        return new UserCollectionsRes(
                collection.getId(),
                collection.getUser().getUsername(),
                collection.getName(),
                collection.isReadyForReservation(),
                collection.isCompleted(),
                BookRes.fromEntities(collection.getBooks())
        );
    }

    public static List<UserCollectionsRes> fromEntities(List<UserCollectionEntity> users) {
        return users.stream().map(UserCollectionsRes::fromEntity).toList();
    }
}
