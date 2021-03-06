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
    private final String trainedFileName;

    public Population(Moon moon, String trainedFileName) {
        this.generation = 1;
        this.trainedFileName = trainedFileName;

        try {
            var loadedBestNeuralNetwork = NeuralNetwork.load(FileUtils.readFile(trainedFileName));
            var loadedSpaceship = new Spaceship(moon, loadedBestNeuralNetwork);
            this.spaceships = IntStream.range(0, Config.POPULATION_SIZE - 1).mapToObj(a -> loadedSpaceship.clone(moon)).collect(Collectors.toList());
            RandomMutations.mutate(this.spaceships);
            this.spaceships.add(0, loadedSpaceship);
        } catch (Exception e) {
            System.out.println("No state to restore");
            this.spaceships = IntStream.range(0, Config.POPULATION_SIZE).mapToObj(a -> new Spaceship(moon)).collect(Collectors.toList());
        }
    }

    public List<Spaceship> getSnapshot() {
        return new ArrayList<>(this.spaceships);
    }

    public Spaceship getLastBest() {
        return lastBest;
    }

    public int getGeneration() {
        return generation;
    }

    public void update(Moon moon) {
        spaceships.stream().parallel().forEach(a -> a.update(moon));
        if (lastBest != null)
            lastBest.update(moon);
    }

    public boolean hasAnyActive() {
        return this.spaceships.stream().anyMatch(a -> a.getStatus() == Spaceship.Status.active);
    }

    public boolean hasAnySuccess() {
        return this.spaceships.stream().anyMatch(a -> a.getStatus() == Spaceship.Status.success);
    }

    public Spaceship getBest(){
        return spaceships.stream().max(Comparator.comparing(Spaceship::getFitness)).orElseThrow();
    }

    public void nextGeneration(Moon moon) {
        Spaceship best = getBest();
        lastBest = best.clone(moon);

        try {
            FileUtils.saveFile(trainedFileName, best.neuralNetwork.save());
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        List<Spaceship> newGen = Tournament
                .draw(spaceships, Spaceship::getFitness, Config.POPULATION_SIZE / 2 - 1, false)
                .stream()
                .flatMap(a -> Arrays.stream(new Spaceship[]{a.clone(moon), a.clone(moon)}))
                .collect(Collectors.toList());

        newGen.add(best.clone(moon));
        RandomMutations.mutate(newGen);
        newGen.add(best.clone(moon));

        this.spaceships = newGen;
        generation++;
    }
}
