package org.codecop.monolith.playground.client;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;
import jakarta.inject.Inject;

@Controller
@Produces(value = MediaType.TEXT_PLAIN)
public class ViewController {

    @Inject
    private AliveListener aliveListener;

    @Get("/grid")
    public HttpResponse<String> grid() {
        char[] grid = ( //
        "          \n" + //
                "          \n" + //
                "          \n" + //
                "          \n" + //
                "          \n" + //
                "          \n" + //
                "          \n" + //
                "          \n" + //
                "          \n" + //
                "          \n" // 
        ).toCharArray();
        aliveListener.livingCells.forEach(p -> grid[p.getY() * (10 + 1) + p.getX()] = '#');
        return HttpResponse.ok(new String(grid));
    }

}
