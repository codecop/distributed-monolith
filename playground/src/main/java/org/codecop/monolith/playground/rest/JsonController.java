package org.codecop.monolith.playground.rest;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;

@Controller
public class JsonController {

    @Get("/hello.json")
    @Produces(value = MediaType.APPLICATION_JSON)
    public HttpResponse<JsonData> helloJson() {
        // see https://itnext.io/building-restful-apis-with-micronaut-98f4eb39211c
        return HttpResponse.ok(new JsonData("Hello World"));
    }

}
