package org.codecop.monolith.playground;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import io.micronaut.transaction.annotation.ReadOnly;
import jakarta.inject.Singleton;

@Singleton
public class GenreRepositoryImpl implements GenreRepository {

    private final EntityManager entityManager;

    public GenreRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @ReadOnly
    public Optional<Student> findById(long id) {
        return Optional.ofNullable(entityManager.find(Student.class, id));
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        findById(id).ifPresent(entityManager::remove);
    }

    @Override
    @Transactional
    public void save(Student s) {
        entityManager.persist(s);
    }

}