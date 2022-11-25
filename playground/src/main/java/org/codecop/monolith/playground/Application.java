package org.codecop.monolith.playground;

import io.micronaut.runtime.Micronaut;

public class Application {
    public static void main(String[] args) {
        // see https://docs.micronaut.io/latest/guide/#runningServer
        Micronaut.run(Application.class, args);
    }
}
