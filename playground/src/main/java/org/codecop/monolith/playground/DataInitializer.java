package org.codecop.monolith.playground;

import java.util.Optional;

import io.micronaut.context.event.ApplicationEventListener;
import io.micronaut.runtime.event.ApplicationStartupEvent;
import io.micronaut.transaction.TransactionOperations;
import jakarta.inject.Inject;

public class DataInitializer implements ApplicationEventListener<ApplicationStartupEvent> {

    @Inject
    private GenreRepository posts;

    @Inject
    private TransactionOperations<?> tx;

    @Override
    public void onApplicationEvent(ApplicationStartupEvent event) {
        tx.executeWrite(status -> {
            this.posts.save(new Student("a"));
            return null;
        });

        tx.executeRead(status -> {
            Optional<Student> s = this.posts.findById(1);
            s.ifPresent(p -> System.out.println(p));
            return null;
        });
    }
}
