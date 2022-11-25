package org.codecop.monolith.playground;

import java.util.Map;
import java.util.Optional;

import io.micronaut.core.util.CollectionUtils;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.views.View;
import jakarta.inject.Inject;

@Controller
public class IssuesController {

    @Inject
    private GenreRepository repository;

    @Get("/hello")
    public String issue() {
        return "Hello World";
    }

    @Get("/issues/{number}")
    public String issue(@PathVariable Integer number) {
        Optional<Student> findById = repository.findById(1);
        System.out.println(findById);

        Student s = new Student("" + number);
        repository.save(s);
        System.out.println(s.id);
        return "Issue # " + number + "!";
    }

    @Get("/http-status")
    public HttpStatus httpStatus() {
        return HttpStatus.CREATED;
        // https://docs.micronaut.io/latest/guide/#statusAnnotation
    }

    @Get("/hello.html")
    @View("home")
    public HttpResponse<Map<?, ?>> issueView() {
        return HttpResponse.ok(CollectionUtils.mapOf("loggedIn", true, "username", "sdelamo"));
        // https://micronaut-projects.github.io/micronaut-views/latest/guide/
    }

}
