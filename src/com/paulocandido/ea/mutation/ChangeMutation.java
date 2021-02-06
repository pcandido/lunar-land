package com.paulocandido.ea.mutation;

import com.paulocandido.utils.SeededRandom;

public class ChangeMutation implements Mutation {

    @Override
    public double mutate(double value) {
        return SeededRandom.getGeneValue();
    }
}
