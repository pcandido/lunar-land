package com.paulocandido.utils;

import java.util.Random;

public class GARandom {

    private static Random random;

    public static Random get() {
        if (random == null) {
            random = new Random();
        }

        return random;
    }

    public static double getGeneValue() {
        return (get().nextGaussian()) * 80;
    }
}
