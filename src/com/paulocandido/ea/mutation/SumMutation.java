package com.paulocandido.ea.mutation;

import com.paulocandido.utils.GARandom;

public class SumMutation implements Mutation {

    @Override
    public double mutate(double value) {
        return value + GARandom.getGeneValue() / 100.0;
    }
}
