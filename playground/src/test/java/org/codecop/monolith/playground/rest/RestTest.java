package org.codecop.monolith.playground.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.runtime.EmbeddedApplication;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;

@MicronautTest
class RestTest {

    @Inject
    EmbeddedApplication<?> application;

    @Test
    void testItWorks() {
        Assertions.assertTrue(application.isRunning());
    }
    
    @Inject
    @Client("/")
    HttpClient client;

    @Test
    void testHelloWorldResponse() {
        String response = client.toBlocking().retrieve(HttpRequest.GET("/hello"));
        assertEquals("Hello World", response);
    }

    @Test
    void testHelloJsonResponse() {
        String response = client.toBlocking().retrieve(HttpRequest.GET("/hello.json"));
        assertEquals("{\"text\":\"Hello World\"}", response);
    }

    @Test
    void testStatusCodeResponse() {
        HttpResponse<?> response = client.toBlocking().exchange(HttpRequest.GET("/http-status"));
        assertEquals(201, response.code());
    }

    @Test
    void testPathVariable() {
        String body = client.toBlocking().retrieve("/return/42");
        assertNotNull(body);
        assertEquals("Issue # 42!", body);
    }

    @Test
    void testShowWithInvalidInteger() {
        HttpClientResponseException e = assertThrows(HttpClientResponseException.class,
                () -> client.toBlocking().exchange("/return/hello"));
        assertEquals(400, e.getStatus().getCode());
    }

    @Test
    void testIssueWithoutNumber() {
        HttpClientResponseException e = assertThrows(HttpClientResponseException.class,
                () -> client.toBlocking().exchange("/return/"));
        assertEquals(404, e.getStatus().getCode());
    }

}
