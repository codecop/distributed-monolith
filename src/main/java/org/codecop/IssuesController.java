package org.codecop;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;

@Controller
public class IssuesController {

    @Get("/hello")
    public String issue() {
        return "Hello World";
    }

    @Get("/{number}")
    public String issue(@PathVariable Integer number) {
        return "Issue # " + number + "!";
    }
}
