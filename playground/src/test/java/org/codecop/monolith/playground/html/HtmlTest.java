package org.codecop.monolith.playground.html;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;

@MicronautTest
class HtmlTest {

    @Inject
    @Client("/")
    HttpClient client;

    @Test
    void testHtmlResponse() {
        String response = client.toBlocking().retrieve(HttpRequest.GET("/hello.html"));
        System.out.println(response);
        assertTrue(response.startsWith("<!DOCTYPE html>"));
    }

}
