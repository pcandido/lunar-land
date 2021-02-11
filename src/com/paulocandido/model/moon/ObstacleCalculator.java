package com.paulocandido.model.moon;

import com.paulocandido.model.Moon;
import com.paulocandido.model.Spaceship;

import java.util.List;
import java.util.function.Function;

public class ObstacleCalculator {

    private final Moon moon;

    public ObstacleCalculator(Moon moon) {
        this.moon = moon;
    }

    private Obstacle getObstacle(int x, int y, Function<Integer, Integer> nextX, Function<Integer, Integer> nextY) {
        int dist = 0;
        int ix = x;
        int iy = y;
        while (true) {
            ix = nextX.apply(ix);
            iy = nextY.apply(iy);
            dist++;

            if (moon.getType(ix, iy) != PointType.air) {
                return new Obstacle(dist, ix, iy);
            }
        }
    }

    public List<Obstacle> calculate(Spaceship spaceship) {
        int x = (int) spaceship.getX();
        int y = (int) spaceship.getY();

        return List.of(
                getObstacle(x, y, a -> a, a -> a - 1),
                getObstacle(x, y, a -> a + 1, a -> a - 1),
                getObstacle(x, y, a -> a + 1, a -> a),
                getObstacle(x, y, a -> a + 1, a -> a + 1),
                getObstacle(x, y, a -> a, a -> a + 1),
                getObstacle(x, y, a -> a - 1, a -> a + 1),
                getObstacle(x, y, a -> a - 1, a -> a),
                getObstacle(x, y, a -> a - 1, a -> a - 1)
        );
    }
}
