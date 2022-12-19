package org.codecop.monolith.playground.jms;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import jakarta.inject.Inject;

@Controller
public class TickController {

    @Inject
    private JmsListener listener;

    @Post("/tick")
    public HttpResponse<?> triggerTick() {
        listener.triggerTick();
        return HttpResponse.status(HttpStatus.CREATED);
    }
}
