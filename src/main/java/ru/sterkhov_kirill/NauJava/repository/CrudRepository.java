package ru.sterkhov_kirill.NauJava.repository;

public interface CrudRepository<T, ID> {

    boolean create(T entity);

    T read(ID id);

    void update(T entity);

    boolean delete(ID id);
}
