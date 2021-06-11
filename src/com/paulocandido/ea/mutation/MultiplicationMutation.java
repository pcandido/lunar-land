package com.paulocandido.ea.mutation;

import com.paulocandido.utils.SeededRandom;

public class MultiplicationMutation implements Mutation {

    @Override
    public double mutate(double value) {
        return value * SeededRandom.get().nextGaussian();
    }
}
