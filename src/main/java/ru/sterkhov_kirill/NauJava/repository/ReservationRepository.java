package ru.sterkhov_kirill.NauJava.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.sterkhov_kirill.NauJava.entity.ReservationEntity;

@RepositoryRestResource
public interface ReservationRepository extends CrudRepository<ReservationEntity, Long> {
}
