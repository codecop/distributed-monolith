package org.codecop.monolith.playground;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;
import jakarta.inject.Inject;

@Controller
@Produces(value = MediaType.APPLICATION_JSON)
public class StatusController {

    @Inject
    private Life status;
    @Inject
    private Position position;

    @Get("/alive.json")
    public HttpResponse<Life> alive() {
        return HttpResponse.ok(status);
    }

    @Get("/position.json")
    public HttpResponse<Position> position() {
        return HttpResponse.ok(position);
    }
}
