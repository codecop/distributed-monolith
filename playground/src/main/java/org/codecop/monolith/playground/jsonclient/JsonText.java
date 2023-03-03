package org.codecop.monolith.playground.jsonclient;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public class JsonText {

    private String text;

    public JsonText() {
    }

    public JsonText(String text) {
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
