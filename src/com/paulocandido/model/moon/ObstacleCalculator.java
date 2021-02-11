package com.paulocandido.model.moon;

import com.paulocandido.model.Moon;
import com.paulocandido.model.Spaceship;

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

    public Obstacle[] aloc() {
        return new Obstacle[8];
    }

    public void calculate(Spaceship spaceship) {
        int x = (int) spaceship.getX();
        int y = (int) spaceship.getY();
        var obstacles = spaceship.getObstacles();

        obstacles[0] = getObstacle(x, y, 0, -1);
        obstacles[1] = getObstacle(x, y, 1, -1);
        obstacles[2] = getObstacle(x, y, 1, 0);
        obstacles[3] = getObstacle(x, y, 1, 1);
        obstacles[4] = getObstacle(x, y, 0, 1);
        obstacles[5] = getObstacle(x, y, -1, 1);
        obstacles[6] = getObstacle(x, y, -1, 0);
        obstacles[7] = getObstacle(x, y, -1, -1);
    }
}
