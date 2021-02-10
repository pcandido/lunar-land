package com.paulocandido.ea.mutation;

public class InversionMutation implements Mutation {

    @Override
    public double mutate(double value) {
        return -value;
    }
}
