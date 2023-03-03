package org.codecop.monolith.playground;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.github.tomakehurst.wiremock.WireMockServer;

import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;

@MicronautTest
class WiremockTest {
    // see https://github.com/rapatao/micronaut-wiremock

    @Inject
    WireMockServer wireMockServer; // at port 9090

    @Inject
    @Client("/")
    HttpClient client;

    @Test
    void how_to_stub_a_server_with_wiremock() {
        wireMockServer.stubFor(get(urlEqualTo("/demo")) //
                .willReturn(aResponse() //
                        .withHeader("Content-Type", "application/json") //
                        .withBody("[\"James\", \"Helen\" ]"))); //

        String[] response = client.toBlocking().retrieve("http://localhost:9090/demo", String[].class);

        assertEquals(2, response.length);
        assertEquals("James", response[0]);
        assertEquals("Helen", response[1]);
    }
}