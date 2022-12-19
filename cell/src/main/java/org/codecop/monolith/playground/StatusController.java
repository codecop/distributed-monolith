package org.codecop.monolith.playground;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;
import jakarta.inject.Inject;

/**
 * Wrapper of the model as JSON controller for test.
 */
@Controller
@Produces(value = MediaType.APPLICATION_JSON)
public class StatusController {

    @Inject
    private Model model;

    @Get("/alive.json")
    public HttpResponse<LifeDto> status() {
        return HttpResponse.ok(model.getLife());
    }

    @Get("/position.json")
    public HttpResponse<Position> position() {
        return HttpResponse.ok(model.getPosition());
    }
}
