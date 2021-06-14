package com.paulocandido.model.spaceship;

import com.paulocandido.model.Moon;
import com.paulocandido.model.Spaceship;

public class SpaceshipDirectionCalculator {

    private static final int RADIUS = 10;

    private Spaceship spaceship;
    private Moon moon;

    public SpaceshipDirectionCalculator(Spaceship spaceship, Moon moon) {
        this.spaceship = spaceship;
        this.moon = moon;
    }

    private double getDistance(int angle) {
        var rRad = Math.toRadians(angle);
        var x = spaceship.getX();
        var y = spaceship.getY();

        var rx = Math.cos(rRad) * RADIUS + x;
        var ry = Math.sin(rRad) * RADIUS + y;

        return moon.getDistance((int) rx, (int) ry);
    }

    public double calculate() {
        int bestAngle = 0;
        double bestDist = Double.POSITIVE_INFINITY;

        for (int angle = 0; angle < 360; angle += 10) {
            double dist = getDistance(angle);
            if (dist < bestDist) {
                bestDist = dist;
                bestAngle = angle;
            }
        }

        return bestAngle;
    }

}
