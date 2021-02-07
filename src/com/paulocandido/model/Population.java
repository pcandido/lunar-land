package com.paulocandido.model;

import com.paulocandido.Config;
import com.paulocandido.ea.RandomMutations;
import com.paulocandido.ea.Tournament;
import com.paulocandido.ia.NeuralNetwork;
import com.paulocandido.utils.FileUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Population {

    private int generation;
    private List<Spaceship> spaceships;
    private Spaceship lastBest;
    private String bestFileName;

    public Population(int size, double noise, double initialFuel, String bestFileName) {
        this.generation = 1;
        this.spaceships = IntStream.range(0, size).mapToObj(a -> new Spaceship()).collect(Collectors.toList());
        this.bestFileName = bestFileName;

        try {
            var loadedBestNeuralNetwork = NeuralNetwork.load(FileUtils.readFile(bestFileName));
            spaceships.set(0, new Spaceship(loadedBestNeuralNetwork));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public List<Spaceship> getSnapshot() {
        return new ArrayList<>(this.spaceships);
    }

    public Spaceship getLastBest() {
        return lastBest;
    }

    public void update(Moon moon) {
        spaceships.stream().parallel().forEach(a -> a.update(moon));
        if (lastBest != null)
            lastBest.update(moon);
    }

    public boolean hasAnyActive() {
        return this.spaceships.stream().anyMatch(a -> a.getStatus() == Spaceship.Status.active);
    }

    public boolean hasAnySuccess(){
        return this.spaceships.stream().anyMatch(a -> a.getStatus() == Spaceship.Status.success);
    }

    public void nextGeneration() {
        Spaceship best = spaceships.stream().max(Comparator.comparing(Spaceship::getFitness)).orElseThrow();
        lastBest = best.clone();

        try {
            FileUtils.saveFile(bestFileName, best.neuralNetwork.save());
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        System.out.printf("%d: %.6f\n", generation, best.getFitness());

        List<Spaceship> newGen = Tournament
                .draw(spaceships, Spaceship::getFitness, Config.POPULATION_SIZE / 2 - 1, false)
                .stream()
                .flatMap(a -> Arrays.stream(new Spaceship[]{a.clone(), a.clone()}))
                .collect(Collectors.toList());

        newGen.add(best.clone());
        RandomMutations.mutate(newGen);
        newGen.add(best.clone());

        this.spaceships = newGen;
        generation++;
    }
}
