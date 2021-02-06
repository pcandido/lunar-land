package com.paulocandido.model;

import com.paulocandido.utils.SeededRandom;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Population {

    private List<Spaceship> spaceships;

    public Population(int size, double noise, double initialFuel) {
        this.spaceships = IntStream.range(0, size).mapToObj(a ->
                new Spaceship(
                        70 + ((SeededRandom.get().nextDouble() - 0.5) * 50 * noise),
                        70 + ((SeededRandom.get().nextDouble() - 0.5) * 50 * noise),
                        0 + ((SeededRandom.get().nextDouble() - 0.5) * 90 * noise),
                        initialFuel
                )
        ).collect(Collectors.toList());
    }

    public List<Spaceship> getSnapshot() {
        return new ArrayList<>(this.spaceships);
    }

    public void update(Moon moon){
        spaceships.forEach(a -> a.update(moon));
    }

}
