package com.paulocandido.model.moon;

import com.paulocandido.model.Moon;
import com.paulocandido.model.Spaceship;

public class ObstacleCalculator {

    private final Moon moon;

    public ObstacleCalculator(Moon moon) {
        this.moon = moon;
    }

    private Obstacle getObstacle(double x, double y, double r) {
        int dist = 0;

        for (; dist <= moon.getMaxObstacleDistance(); dist += 50) {
            double radR = Math.toRadians(r);

            int ox = (int) Math.round(Math.cos(radR) * dist + x);
            int oy = (int) Math.round(Math.sin(radR) * dist + y);

            if (moon.getType(ox, oy) != PointType.air) {
                dist -= 50;
                break;
            }
        }

        for (; dist <= moon.getMaxObstacleDistance(); dist++) {
            double radR = Math.toRadians(r);

            int ox = (int) Math.round(Math.cos(radR) * dist + x);
            int oy = (int) Math.round(Math.sin(radR) * dist + y);

            if (moon.getType(ox, oy) != PointType.air) {
                return new Obstacle(dist, ox, oy);
            }
        }

        throw new RuntimeException("Did not find obstacle");
    }

    public Obstacle[] aloc() {
        return new Obstacle[8];
    }

    public void calculate(Spaceship spaceship) {
        var obstacles = spaceship.getObstacles();

        for (int i = 0; i < 8; i++) {
            obstacles[i] = getObstacle(spaceship.getX(), spaceship.getY(), spaceship.getR() + (i * 45));
        }
    }
}
