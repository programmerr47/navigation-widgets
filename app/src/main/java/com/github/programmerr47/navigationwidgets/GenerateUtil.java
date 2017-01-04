package com.github.programmerr47.navigationwidgets;

import java.util.Random;

public class GenerateUtil {
    private GenerateUtil() {}

    private static final Random rand = new Random();

    public static <T> T generateFrom(T... variants) {
        int index = rand.nextInt(variants.length);
        return variants[index];
    }
}
