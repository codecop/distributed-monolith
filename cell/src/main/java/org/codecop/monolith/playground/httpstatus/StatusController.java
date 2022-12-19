package org.codecop.monolith.playground.httpstatus;

import org.codecop.monolith.playground.gol.Life;
import org.codecop.monolith.playground.gol.Cell;
import org.codecop.monolith.playground.gol.Position;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;
import jakarta.inject.Inject;

/**
 * Read-only wrapper of the model as JSON controller (for test).
 */
@Controller
@Produces(value = MediaType.APPLICATION_JSON)
public class StatusController {

    @Inject
    private Cell cell;

    @Get("/alive.json")
    public HttpResponse<LifeResource> status() {
        return HttpResponse.ok(toResource(cell.getLife()));
    }

    private LifeResource toResource(Life value) {
        return new LifeResource(value.isAlive());
    }

    @Get("/position.json")
    public HttpResponse<PositionResource> position() {
        return HttpResponse.ok(toResource(cell.getPosition()));
    }

    private PositionResource toResource(Position value) {
        return new PositionResource(value.getX(), value.getY());
    }

}
