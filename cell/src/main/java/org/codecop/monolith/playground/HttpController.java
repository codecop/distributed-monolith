package org.codecop.monolith.playground;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;
import jakarta.inject.Inject;

@Controller
public class HttpController {

    @Inject
    private Position position;
    
    @Get("/position.json")
    @Produces(value = MediaType.APPLICATION_JSON)
    public HttpResponse<Position> helloJson() {
        return HttpResponse.ok(position);
    }

}
