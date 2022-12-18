package org.codecop.monolith.playground;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public class JsonData {

    private String text;

    public JsonData() {
    }

    public JsonData(String text) {
        super();
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
