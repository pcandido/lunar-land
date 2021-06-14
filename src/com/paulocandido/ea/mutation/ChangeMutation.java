package com.paulocandido.ea.mutation;

import com.paulocandido.utils.GARandom;

public class ChangeMutation implements Mutation {

    @Override
    public double mutate(double value) {
        return GARandom.getGeneValue();
    }
}
