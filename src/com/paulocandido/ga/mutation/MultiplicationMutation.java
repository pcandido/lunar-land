package com.paulocandido.ga.mutation;

import com.paulocandido.utils.SeededRandom;

public class MultiplicationMutation implements Mutation {

    @Override
    public double mutate(double value) {
        return value * (SeededRandom.get().nextDouble() * 4 - 2);
    }
}
