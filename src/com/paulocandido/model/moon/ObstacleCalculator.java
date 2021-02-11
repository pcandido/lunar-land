package com.paulocandido.model.moon;

import com.paulocandido.model.Moon;
import com.paulocandido.model.Spaceship;

import java.util.List;

public class ObstacleCalculator {

    private final Moon moon;

    public ObstacleCalculator(Moon moon) {
        this.moon = moon;
    }

    private Obstacle getObstacle(int x, int y, int xIncrement, int yIncrement) {
        int dist = 0;
        int ix = x;
        int iy = y;
        while (true) {
            ix += xIncrement;
            iy += yIncrement;
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
                getObstacle(x, y, 0, -1),
                getObstacle(x, y, 1, -1),
                getObstacle(x, y, 1, 0),
                getObstacle(x, y, 1, 1),
                getObstacle(x, y, 0, 1),
                getObstacle(x, y, -1, 1),
                getObstacle(x, y, -1, 0),
                getObstacle(x, y, -1, -1)
        );
    }
}
