package com.paulocandido.ia;

import com.paulocandido.ea.mutation.Mutation;
import com.paulocandido.utils.GARandom;

public class Perceptron {

    private double[] weights;
    private double bias;
    private final ActivationFunction activationFunction;

    public Perceptron(int size, ActivationFunction activationFunction) {
        this.activationFunction = activationFunction;
        this.bias = GARandom.getGeneValue();

        this.weights = new double[size];
        for (int i = 0; i < size; i++) {
            this.weights[i] = GARandom.getGeneValue();
        }
    }

    public Perceptron(double[] weights, double bias, ActivationFunction activationFunction) {
        this.weights = weights;
        this.bias = bias;
        this.activationFunction = activationFunction;
    }

    public double calculate(double[] values) {
        double sum = bias;
        for (int i = 0; i < weights.length; i++) {
            sum += weights[i] * values[i];
        }

        return activationFunction.calculate(sum);
    }

    public void mutate(Mutation mutation) {
        int gene = GARandom.get().nextInt(weights.length + 1);
        if (gene == weights.length)
            bias = mutation.mutate(bias);
        else
            weights[gene] = mutation.mutate(weights[gene]);
    }

    @SuppressWarnings("MethodDoesntCallSuperMethod")
    public Perceptron clone() {
        double[] clonedPesos = new double[weights.length];
        System.arraycopy(weights, 0, clonedPesos, 0, weights.length);
        return new Perceptron(clonedPesos, bias, activationFunction);
    }

    public String save() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.activationFunction.toString());
        sb.append(" ");
        sb.append(this.bias);

        for (double weight : this.weights) {
            sb.append(" ");
            sb.append(weight);
        }

        return sb.toString();
    }

    public static Perceptron load(String value) {
        var parts = value.split(" ");
        var activationFunction = ActivationFunction.valueOf(parts[0]);
        var bias = Double.parseDouble(parts[1]);
        var weights = new double[parts.length - 2];
        for (int i = 2, j = 0; i < parts.length; i++, j++) {
            weights[j] = Double.parseDouble(parts[i]);
        }

        return new Perceptron(weights, bias, activationFunction);
    }
}
