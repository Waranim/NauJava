package ru.sterkhov_kirill.NauJava.repository;

import org.springframework.data.repository.CrudRepository;
import ru.sterkhov_kirill.NauJava.entity.CheckoutEntity;

public interface CheckoutRepository extends CrudRepository<CheckoutEntity, Long> {
}
