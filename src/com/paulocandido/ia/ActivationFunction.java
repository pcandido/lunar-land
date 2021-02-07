package com.paulocandido.ia;

import java.util.function.Function;

public enum ActivationFunction {

    relu(a -> a < 0 ? 0d : a),
    reluDx(a -> a < 0 ? 0d : 1d);

    private final Function<Double, Double> function;

    ActivationFunction(Function<Double, Double> function) {
        this.function = function;
    }

    double calculate(double value) {
        return function.apply(value);
    }
}
