package org.codecop.monolith.playground.kafka;

import io.micronaut.core.annotation.Creator;
import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public class BookAnalytics {

    private final String bookIsbn;
    private final long count;

    @Creator
    public BookAnalytics(String bookIsbn, long count) {
        this.bookIsbn = bookIsbn;
        this.count = count;
    }

    public String getBookIsbn() {
        return bookIsbn;
    }

    public long getCount() {
        return count;
    }

    @Override
    public String toString() {
        return getBookIsbn() + ": " + getCount();
    }
}
