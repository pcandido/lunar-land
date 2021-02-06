package com.paulocandido.ga;

import com.paulocandido.utils.SeededRandom;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Tournament {

    public static <T> List<T> draw(List<T> items, GetFitness<T> getFitness, int qty, boolean reversed) {
        var mapped = items.stream().map(a -> new Map<>(a, getFitness.apply(a))).collect(Collectors.toCollection(ArrayList::new));
        var drown = new ArrayList<T>();

        for (int i = 0; i < qty; i++) {
            drown.add(draw(mapped).obj);
        }

        return drown;
    }


    public static <T> T draw(List<T> items, GetFitness<T> getFitness, boolean reversed) {
        var mapped = items.stream().map(a -> new Map<>(a, getFitness.apply(a))).collect(Collectors.toList());
        return draw(mapped).obj;
    }

    private static <T> Map<T> draw(List<Map<T>> items) {

        if (items.size() == 1)
            return items.get(0);

        if (items.size() == 2)
            return fight(items.get(0), items.get(1));

        return fight(
                draw(items.subList(0, items.size() / 2)),
                draw(items.subList(items.size() / 2, items.size()))
        );
    }

    private static <T> Map<T> fight(Map<T> item1, Map<T> item2) {
        var sum = item1.fitness + item2.fitness;
        var drown = SeededRandom.get().nextDouble() * sum;

        if (drown < item1.fitness)
            return item1;
        else
            return item2;
    }

    public interface GetFitness<T> {
        double apply(T obj);
    }

    private static class Map<T> {

        private final T obj;
        private final double fitness;

        Map(T obj, double fitness) {
            this.obj = obj;
            this.fitness = fitness;
        }

    }

}
