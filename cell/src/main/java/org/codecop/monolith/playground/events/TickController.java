package org.codecop.monolith.playground.events;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import jakarta.inject.Inject;

@Controller
public class TickController {

    @Inject
    private Time time;
    @Inject
    private TickProducer ticker;

    @Post("/tick")
    public HttpResponse<?> triggerTick() {
        ticker.tick(time.getCurrent() + 1);
        return HttpResponse.status(HttpStatus.CREATED);
    }
}
