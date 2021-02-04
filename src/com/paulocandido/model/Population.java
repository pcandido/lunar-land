package com.paulocandido.model;

import com.paulocandido.utils.SeededRandom;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Population {

    private List<Spaceship> spaceships;

    public Population(int size, double noise) {
        this.spaceships = IntStream.range(0, size).mapToObj(a ->
                new Spaceship(
                        30 + ((SeededRandom.get().nextDouble() - 0.5) * 50 * noise),
                        30 + ((SeededRandom.get().nextDouble() - 0.5) * 50 * noise),
                        0 + ((SeededRandom.get().nextDouble() - 0.5) * 90 * noise)
                )
        ).collect(Collectors.toList());
    }

    public List<Spaceship> getSnapshot() {
        return new ArrayList<>(this.spaceships);
    }

}
