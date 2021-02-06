package com.paulocandido.model;

import com.paulocandido.Config;
import com.paulocandido.ea.RandomMutations;
import com.paulocandido.ea.Tournament;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Population {

    private int generation;
    private List<Spaceship> spaceships;

    public Population(int size, double noise, double initialFuel) {
        this.generation = 1;
        this.spaceships = IntStream.range(0, size).mapToObj(a -> new Spaceship()).collect(Collectors.toList());
    }

    public List<Spaceship> getSnapshot() {
        return new ArrayList<>(this.spaceships);
    }

    public void update(Moon moon) {
        spaceships.forEach(a -> a.update(moon));
    }

    public boolean isAnyActive() {
        return this.spaceships.stream().anyMatch(a -> a.getStatus() == Spaceship.Status.active);
    }

    public void nextGeneration() {
        Spaceship best = spaceships.stream().max(Comparator.comparing(Spaceship::getFitness)).orElseThrow();
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
