package org.codecop.monolith.playground.events;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Consumes;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import jakarta.inject.Inject;

@Controller
public class SeedController {

    @Inject
    private JmsListener listener;

    @Post("/seed")
    @Consumes(value = MediaType.ALL)
    public HttpResponse<?> seed() {
        listener.seed();
        return HttpResponse.status(HttpStatus.CREATED);
    }
}
