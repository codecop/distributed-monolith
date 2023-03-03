package org.codecop.monolith.playground.jsonclient;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import com.github.tomakehurst.wiremock.WireMockServer;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;

@MicronautTest(environments = { "wiremock" })
class JsonRestClientTest {

    @Inject
    WireMockServer wireMockServer; // at port 9090

    @Inject
    JsonRestClient producer;

    @Test
    void testHelloJsonResponse() {
        wireMockServer.stubFor(get(urlEqualTo("/hello.json")) //
                .willReturn(aResponse() //
                        .withHeader("Content-Type", "application/json") //
                        .withBody("{\"text\":\"Hello Json\"}")));

        JsonText[] actual = { null };
        producer.fetchReleases().subscribe(new Subscriber<JsonText>() {
            @Override
            public void onSubscribe(Subscription s) {
                // System.out.println("onSubscribe " + s);
                s.request(1);
            }

            @Override
            public void onNext(JsonText t) {
                // System.out.println("onNext " + t);
                actual[0] = t;
            }

            @Override
            public void onError(Throwable t) {
                // System.out.println("onError " + t);
            }

            @Override
            public void onComplete() {
                // System.out.println("onComplete");
            }
        });

        await().atMost(1, TimeUnit.SECONDS).until(() -> actual[0] != null);

        assertEquals("Hello Json", actual[0].getText());
    }

}
