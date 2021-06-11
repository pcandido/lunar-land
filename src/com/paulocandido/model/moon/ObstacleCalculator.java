package com.paulocandido.model.moon;

import com.paulocandido.Config;
import com.paulocandido.model.Moon;
import com.paulocandido.model.Spaceship;

public class ObstacleCalculator {

    private final Moon moon;

    public ObstacleCalculator(Moon moon) {
        this.moon = moon;
    }

    private Obstacle getObstacleBinarySearch(double x, double y, double r, int distDown, int distUp) {
        double radR = Math.toRadians(r);
        int dist = ((distUp - distDown) / 2) + distDown;

        int ox = (int) Math.round(Math.cos(radR) * dist + x);
        int oy = (int) Math.round(Math.sin(radR) * dist + y);

        boolean air = moon.getType(ox, oy) == PointType.air;

        if (distUp - distDown < 2) {
            if (air)
                return getObstacleBinarySearch(x, y, r, distDown + 1, distDown + 1);
            else
                return new Obstacle(dist, ox, oy);
        }

        if (air)
            return getObstacleBinarySearch(x, y, r, dist, distUp);
        else
            return getObstacleBinarySearch(x, y, r, distDown, dist);
    }

    private Obstacle getObstacleHeuristic(double x, double y, double r) {
        return getObstacleBinarySearch(x, y, r, 0, moon.getMaxObstacleDistance());
    }

    private Obstacle getObstacleDeterministic(double x, double y, double r) {
        for (int dist = 0; dist <= moon.getMaxObstacleDistance(); dist++) {
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
            if (Config.USE_HEURISTIC_OBSTACLE_CALCULATOR)
                obstacles[i] = getObstacleHeuristic(spaceship.getX(), spaceship.getY(), spaceship.getR() + (i * 45));
            else
                obstacles[i] = getObstacleDeterministic(spaceship.getX(), spaceship.getY(), spaceship.getR() + (i * 45));
        }
    }
}
