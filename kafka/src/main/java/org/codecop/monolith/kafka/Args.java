package org.codecop.monolith.kafka;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Args {

    public static String[] from(List<String> prefix, String[] args) {
        List<String> a = new ArrayList<>();
        a.addAll(prefix);
        a.addAll(Arrays.asList(args));
        return a.toArray(new String[0]);
    }

}
