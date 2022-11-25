package org.codecop.monolith.playground;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import jakarta.inject.Singleton;

@Singleton
public class TextStore {

    List<String> messages = Collections.synchronizedList(new ArrayList<>());

}
