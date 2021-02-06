package com.paulocandido.ia.activation;

public class Relu implements ActivationFunction {

    @Override
    public double calculate(double value) {
        if (value < 0) {
            return 0;
        } else {
            return value;
        }
    }
}
