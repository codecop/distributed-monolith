package org.codecop.monolith.playground.jms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import jakarta.inject.Singleton;

@Singleton
public class TextStore {

    public List<String> messages = Collections.synchronizedList(new ArrayList<>());

}
