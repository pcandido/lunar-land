package com.paulocandido.utils;

import com.paulocandido.model.Moon;

import java.util.LinkedList;
import java.util.Queue;

public class Wavefront {

    private void actOnNeighbors(int i, int j, int width, int height, NeighborAction action) {
        if (i - 1 >= 0) action.act(i - 1, j);
        if (j - 1 >= 0) action.act(i, j - 1);
        if (i + 1 < width) action.act(i + 1, j);
        if (j + 1 < height) action.act(i, j + 1);
    }

    public int[][] getDistances(Moon.Type[][] types) {
        var distances = new int[types.length][types[0].length];
        var width = types.length;
        var height = types[0].length;

        Queue<int[]> queue = new LinkedList<>();

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (types[i][j] == Moon.Type.ground) {
                    distances[i][j] = -1;
                } else if (types[i][j] == Moon.Type.air) {
                    distances[i][j] = Integer.MAX_VALUE;
                } else if (types[i][j] == Moon.Type.station) {
                    distances[i][j] = 0;
                    actOnNeighbors(i, j, width, height, (ni, nj) -> queue.add(new int[]{ni, nj, 1}));
                }
            }
        }

        while (!queue.isEmpty()) {
            var current = queue.remove();
            var i = current[0];
            var j = current[1];
            var distance = current[2];

            if (distances[i][j] > distance) {
                distances[i][j] = distance;
                actOnNeighbors(i, j, width, height, (ni, nj) -> queue.add(new int[]{ni, nj, distance + 1}));
            }
        }

        return distances;
    }

    private interface NeighborAction {
        void act(int i, int j);
    }

}
