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
        return HttpResponse.ok(toDto(model.getLife()));
    }

    @Get("/position.json")
    public HttpResponse<PositionDto> position() {
        return HttpResponse.ok(toDto(model.getPosition()));
    }

    private static LifeDto toDto(Life value) {
        return new LifeDto(value.isAlive());
    }

    private static PositionDto toDto(Position value) {
        return new PositionDto(value.getX(), value.getY());
    }

}
