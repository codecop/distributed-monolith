package org.codecop.monolith.playground.jpa;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;

@MicronautTest
class JpaTest {

    @Inject
    @Client("/")
    HttpClient client;

    @Test
    void testJpa() {
        String body = client.toBlocking().retrieve("/jpa/12");
        assertNotNull(body);
        assertEquals("Issue # 12!", body);

        // do again to see save
        client.toBlocking().retrieve("/jpa/12");
    }

}
