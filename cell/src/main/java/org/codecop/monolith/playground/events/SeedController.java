package org.codecop.monolith.playground.events;

import org.codecop.monolith.playground.gol.Cell;

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
    private Cell cell;
    @Inject
    private ClockedPositionConverter converter;
    @Inject
    private SeedProducer seeder;

    @Post("/seed")
    @Consumes(value = MediaType.ALL)
    public HttpResponse<?> seed() {
        seeder.seed(converter.toDto(0, cell.getPosition()));
        return HttpResponse.status(HttpStatus.CREATED);
    }
}
