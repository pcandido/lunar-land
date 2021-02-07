package com.paulocandido.ia;

import com.paulocandido.ea.mutation.Mutation;
import com.paulocandido.utils.SeededRandom;

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

    public String save() {
        StringBuilder sb = new StringBuilder();
        for (Perceptron perceptron : perceptrons) {
            sb.append(perceptron.save());
            sb.append("\n");
        }
        return sb.toString().trim();
    }

    public static Layer load(String value) {
        var parts = value.split("\n");
        var perceptrons = new Perceptron[parts.length];

        for (int i = 0; i < parts.length; i++) {
            perceptrons[i] = Perceptron.load(parts[i]);
        }

        return new Layer(perceptrons);
    }
}
