package com.paulocandido.ia;

import com.paulocandido.ea.mutation.Mutation;
import com.paulocandido.utils.SeededRandom;

public class NeuralNetwork {

    private final int inputLayerSize;
    private final Layer[] hiddenLayers;
    private final Layer outputLayer;

    public NeuralNetwork(int input, int output, int... hidden) {

        this.inputLayerSize = input;

        hiddenLayers = new Layer[hidden.length];
        for (int i = 0; i < hidden.length; i++) {
            hiddenLayers[i] = new Layer(hidden[i], i == 0 ? input : hidden[i - 1], ActivationFunction.relu);
        }
        outputLayer = new Layer(output, hidden[hidden.length - 1], ActivationFunction.reluDx);
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

    @SuppressWarnings("MethodDoesntCallSuperMethod")
    public NeuralNetwork clone() {
        Layer[] hidden = new Layer[hiddenLayers.length];
        for (int i = 0; i < hiddenLayers.length; i++) {
            hidden[i] = hiddenLayers[i].clone();
        }

        return new NeuralNetwork(inputLayerSize, hidden, outputLayer.clone());
    }

    public String save() {
        StringBuilder sb = new StringBuilder();

        sb.append(inputLayerSize);
        sb.append("\n---\n");
        sb.append(outputLayer.save());
        for (Layer hidden : this.hiddenLayers) {
            sb.append("\n---\n");
            sb.append(hidden.save());
        }

        return sb.toString();
    }

    public static NeuralNetwork load(String value) {
        var parts = value.split("---");
        var inputSize = Integer.parseInt(parts[0].trim());
        var output = Layer.load(parts[1].trim());
        var hidden = new Layer[parts.length - 2];

        for (int i = 2, j = 0; i < parts.length; i++, j++) {
            hidden[j] = Layer.load(parts[i].trim());
        }

        return new NeuralNetwork(inputSize, hidden, output);
    }

}

