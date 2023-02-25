package org.codecop.monolith.playground.rest;

import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;

@Controller
public class RestController {

    @Get("/hello")
    public String hello() {
        return "Hello World";
    }

    @Get("/return/{number}")
    public String issue(@PathVariable Integer number) {
        return "Issue # " + number + "!";
    }

    @Get("/http-status")
    public HttpStatus httpStatus() {
        // see https://docs.micronaut.io/latest/guide/#statusAnnotation
        return HttpStatus.CREATED;
    }

}
