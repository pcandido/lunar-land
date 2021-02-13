package com.paulocandido.utils;

import java.util.Random;

public class SeededRandom {

    private static Random random;

    public static Random get() {
        if (random == null) {
            random = new Random(2);
        }

        return random;
    }

    public static double getGeneValue() {
        return (get().nextDouble() - 0.5) * 100;
    }
}
