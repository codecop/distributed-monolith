package org.codecop.monolith.playground.http;

import org.codecop.monolith.playground.gol.Life;
import org.codecop.monolith.playground.gol.Model;
import org.codecop.monolith.playground.gol.Position;

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
    public HttpResponse<LifeResource> status() {
        return HttpResponse.ok(toResource(model.getLife()));
    }

    @Get("/position.json")
    public HttpResponse<PositionResource> position() {
        return HttpResponse.ok(toResource(model.getPosition()));
    }

    private static LifeResource toResource(Life value) {
        return new LifeResource(value.isAlive());
    }

    private static PositionResource toResource(Position value) {
        return new PositionResource(value.getX(), value.getY());
    }

}
