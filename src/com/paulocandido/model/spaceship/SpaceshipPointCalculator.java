package com.paulocandido.model.spaceship;

import com.paulocandido.model.Spaceship;

public class SpaceshipPointCalculator {

    private static record Model(double xDelta, double yDelta, boolean landingGear) {
    }

    private static final Model[] POINTS = {
            new Model(-0.5, 0.5, true),
            new Model(0, 0.5, true),
            new Model(0.5, 0.5, true),
            new Model(0.44, 0.19, false),
            new Model(0.28, -0.17, false),
            new Model(0.33, -0.44, false),
            new Model(0, -0.5, false),
            new Model(-0.20, -0.49, false),
            new Model(-0.28, -0.17, false),
            new Model(-0.44, 0.19, false)
    };

    public static SpaceshipPoint[] aloc() {
        return new SpaceshipPoint[POINTS.length];
    }

    public static void calculate(Spaceship spaceship) {
        var points = spaceship.getPoints();

        for (int i = 0; i < POINTS.length; i++) {
            var rRad = Math.toRadians(360 - spaceship.getR());
            var x = Spaceship.WIDTH * POINTS[i].xDelta;
            var y = Spaceship.HEIGHT * POINTS[i].yDelta;

            var rx = x * Math.cos(rRad) + y * Math.sin(rRad);
            var ry = -x * Math.sin(rRad) + y * Math.cos(rRad);

            points[i] = new SpaceshipPoint(
                    spaceship.getX() + rx,
                    spaceship.getY() + ry,
                    POINTS[i].landingGear
            );
        }
    }

}
