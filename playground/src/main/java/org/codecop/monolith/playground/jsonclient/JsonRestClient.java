package org.codecop.monolith.playground.jsonclient;

import static io.micronaut.http.HttpHeaders.USER_AGENT;

import org.reactivestreams.Publisher;

import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Header;
import io.micronaut.http.client.annotation.Client;

@Client(id = "foo")
@Header(name = USER_AGENT, value = "Micronaut HTTP Client")
public interface JsonRestClient {

    // see https://guides.micronaut.io/latest/micronaut-http-client-maven-java.html

    @Get("/hello.json")
    Publisher<JsonText> fetchReleases();
}
