package com.paulocandido.ia.activation;

public class ReluDx implements ActivationFunction {

    @Override
    public double calculate(double value) {
        if (value < 0) {
            return 0;
        } else {
            return 1;
        }
    }
}
