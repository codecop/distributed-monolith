package org.codecop;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.runtime.EmbeddedApplication;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;

@MicronautTest
class DistributedMonolithTest {

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
        // see https://docs.micronaut.io/latest/guide/#runningServer
    }

    @Test
    void testHelloWorldHtmlResponse() {
        String response = client.toBlocking().retrieve(HttpRequest.GET("/hello.html"));
        System.out.println(response);
        assertTrue(response.startsWith("<!DOCTYPE html>"));
    }

    @Test
    public void testIssue() {
        String body = client.toBlocking().retrieve("/issues/12");
        assertNotNull(body);
        assertEquals("Issue # 12!", body);

        // do again to see save
        client.toBlocking().retrieve("/issues/12");
    }

    @Test
    public void testShowWithInvalidInteger() {
        HttpClientResponseException e = assertThrows(HttpClientResponseException.class,
                () -> client.toBlocking().exchange("/issues/hello"));
        assertEquals(400, e.getStatus().getCode());
    }

    @Test
    public void testIssueWithoutNumber() {
        HttpClientResponseException e = assertThrows(HttpClientResponseException.class,
                () -> client.toBlocking().exchange("/issues/"));
        assertEquals(404, e.getStatus().getCode());
    }
}
