package com.paulocandido.ea.mutation;

import com.paulocandido.utils.GARandom;

public class MultiplicationMutation implements Mutation {

    @Override
    public double mutate(double value) {
        return value + (value * GARandom.get().nextGaussian());
    }
}
