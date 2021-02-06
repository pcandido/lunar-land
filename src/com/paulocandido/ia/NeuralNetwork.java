package com.paulocandido.ia;

import com.paulocandido.ga.mutation.Mutation;
import com.paulocandido.ia.activation.Relu;
import com.paulocandido.ia.activation.ReluDx;
import com.paulocandido.utils.SeededRandom;

public class NeuralNetwork {

    private final int inputLayerSize;
    private final Layer[] hiddenLayers;
    private final Layer outputLayer;

    public NeuralNetwork(int input, int output, int... hidden) {

        this.inputLayerSize = input;

        hiddenLayers = new Layer[hidden.length];
        for (int i = 0; i < hidden.length; i++) {
            hiddenLayers[i] = new Layer(hidden[i], i == 0 ? input : hidden[i - 1], new Relu());
        }
        outputLayer = new Layer(output, hidden[hidden.length - 1], new ReluDx());
    }

    public NeuralNetwork(int inputLayerSize, Layer[] hiddenLayers, Layer outputLayer) {
        this.inputLayerSize = inputLayerSize;
        this.hiddenLayers = hiddenLayers;
        this.outputLayer = outputLayer;
    }

    public double[] calculate(double[] input) {

        if (input.length != inputLayerSize) {
            throw new RuntimeException("Tamanho de entrada inesperado");
        }

        double[] values = input;
        for (Layer layer : hiddenLayers) {
            values = layer.calculate(values);
        }
        values = outputLayer.calculate(values);

        return values;
    }

    public void mutate(Mutation mutation) {
        int layer = SeededRandom.get().nextInt(hiddenLayers.length + 1);
        if (layer == hiddenLayers.length)
            outputLayer.mutate(mutation);
        else
            hiddenLayers[layer].mutate(mutation);
    }

    public NeuralNetwork clone() {
        Layer[] hidden = new Layer[hiddenLayers.length];
        for (int i = 0; i < hiddenLayers.length; i++) {
            hidden[i] = hiddenLayers[i].clone();
        }

        return new NeuralNetwork(inputLayerSize, hidden, outputLayer.clone());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Layer layer : hiddenLayers) {
            sb.append("[");
            sb.append(layer.toString());
            sb.append("] ");
        }
        sb.append("[");
        sb.append(outputLayer.toString());
        sb.append("]");

        return sb.toString();
    }
}

