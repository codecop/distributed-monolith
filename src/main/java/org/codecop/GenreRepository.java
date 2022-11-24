package org.codecop;

import java.util.Optional;

public interface GenreRepository {

    Optional<Student> findById(long id);

    void deleteById(long id);

    void save(Student s);
}
