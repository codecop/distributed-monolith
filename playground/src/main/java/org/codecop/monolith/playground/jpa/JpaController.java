package org.codecop.monolith.playground.jpa;

import java.util.Optional;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import jakarta.inject.Inject;

@Controller
public class JpaController {

    @Inject
    private GenreRepository repository;
    @Inject
    private RemoteRepository remoteRepo;

    @Get("/jpa/{number}")
    public String issue(@PathVariable Integer number) {
        Optional<Student> findById = repository.findById(1);
        System.out.println("JpaController: " + findById);

        Student s = new Student("" + number);
        repository.save(s);
        remoteRepo.save(new Student("remote " + number));
        System.out.println("JpaController: id=" + s.id);
        return "Issue # " + number + "!";
    }

}
