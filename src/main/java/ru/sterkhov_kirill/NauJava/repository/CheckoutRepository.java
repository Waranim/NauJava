package ru.sterkhov_kirill.NauJava.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.sterkhov_kirill.NauJava.entity.CheckoutEntity;

@RepositoryRestResource
public interface CheckoutRepository extends CrudRepository<CheckoutEntity, Long> {
}
