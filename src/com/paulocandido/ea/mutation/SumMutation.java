package com.paulocandido.ea.mutation;

import com.paulocandido.utils.SeededRandom;

public class SumMutation implements Mutation {

    @Override
    public double mutate(double value) {
        return value + SeededRandom.getGeneValue() / 100.0;
    }
}
