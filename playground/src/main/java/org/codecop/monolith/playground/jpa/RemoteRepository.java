package org.codecop.monolith.playground.jpa;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

@Repository("remote")
public interface RemoteRepository extends CrudRepository<Student, Long> {

}
