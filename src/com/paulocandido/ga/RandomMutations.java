package com.paulocandido.ga;

import com.paulocandido.Config;
import com.paulocandido.ga.mutation.ChangeMutation;
import com.paulocandido.ga.mutation.MultiplicationMutation;
import com.paulocandido.ga.mutation.Mutation;
import com.paulocandido.ga.mutation.SumMutation;
import com.paulocandido.model.Spaceship;
import com.paulocandido.utils.SeededRandom;

import java.util.List;

public class RandomMutations {

    public static void mutate(List<Spaceship> spaceships) {

        spaceships.forEach(a -> {
            int mutations = SeededRandom.get().nextInt(Config.MAX_NUMBER_MUTATIONS + 1);
            for (int i = 0; i < mutations; i++) {
                Mutation mutation = switch (SeededRandom.get().nextInt(3)) {
                    case 1 -> new MultiplicationMutation();
                    case 2 -> new SumMutation();
                    default -> new ChangeMutation();
                };

                a.getNeuralNetwork().mutate(mutation);
            }
        });

    }

}
