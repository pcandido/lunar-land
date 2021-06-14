package com.paulocandido.ea;

import com.paulocandido.Config;
import com.paulocandido.ea.mutation.*;
import com.paulocandido.model.Spaceship;
import com.paulocandido.utils.GARandom;

import java.util.List;

public class RandomMutations {

    public static void mutate(List<Spaceship> spaceships) {

        spaceships.forEach(a -> {
            int mutations = GARandom.get().nextInt(Config.MAX_NUMBER_MUTATIONS + 1);
            for (int i = 0; i < mutations; i++) {
                Mutation mutation = switch (GARandom.get().nextInt(4)) {
                    case 1 -> new MultiplicationMutation();
                    case 2 -> new SumMutation();
                    case 3 -> new InversionMutation();
                    default -> new ChangeMutation();
                };

                a.getNeuralNetwork().mutate(mutation);
            }
        });

    }

}
