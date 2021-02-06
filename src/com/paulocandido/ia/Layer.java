package com.paulocandido.ia;

import com.paulocandido.ea.mutation.Mutation;
import com.paulocandido.ia.activation.ActivationFunction;
import com.paulocandido.utils.SeededRandom;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Layer {

    private final Perceptron[] perceptrons;

    public Layer(int qtyPerceptrons, int sizePreviousLayer, ActivationFunction activationFunction) {
        perceptrons = new Perceptron[qtyPerceptrons];

        for (int i = 0; i < qtyPerceptrons; i++) {
            perceptrons[i] = new Perceptron(sizePreviousLayer, activationFunction);
        }
    }

    public Layer(Perceptron[] perceptrons) {
        this.perceptrons = perceptrons;
    }

    public double[] calculate(double[] values) {
        double[] saida = new double[perceptrons.length];

        for (int i = 0; i < perceptrons.length; i++) {
            saida[i] = perceptrons[i].calculate(values);
        }

        return saida;
    }

    public void mutate(Mutation mutation) {
        perceptrons[SeededRandom.get().nextInt(perceptrons.length)].mutate(mutation);
    }

    @SuppressWarnings("MethodDoesntCallSuperMethod")
    public Layer clone() {
        Perceptron[] cloned = new Perceptron[perceptrons.length];
        for (int i = 0; i < perceptrons.length; i++) {
            cloned[i] = perceptrons[i].clone();
        }

        return new Layer(cloned);
    }

    @Override
    public String toString() {
        return Arrays.stream(perceptrons).map(a -> "( " + a.toString() + " )").collect(Collectors.joining(" "));
    }
}
