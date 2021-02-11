package com.paulocandido.model.spaceship;

import com.paulocandido.model.Spaceship;

import java.util.List;
import java.util.stream.Collectors;

public class SpaceshipPointCalculator {

    private static record Model(double xDelta, double yDelta, boolean landingGear) {
    }

    private static final List<Model> POINTS = List.of(
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
    );
    private static final Model DISTANCE_POINT = POINTS.get(1);

    public static List<SpaceshipPoint> calculate(Spaceship spaceship) {
        return POINTS.stream().map(a -> {
            var rRad = Math.toRadians(360 - spaceship.getR());
            var x = Spaceship.WIDTH * a.xDelta;
            var y = Spaceship.HEIGHT * a.yDelta;

            var rx = x * Math.cos(rRad) + y * Math.sin(rRad);
            var ry = -x * Math.sin(rRad) + y * Math.cos(rRad);

            return new SpaceshipPoint(
                    spaceship.getX() + rx,
                    spaceship.getY() + ry,
                    a.landingGear
            );
        }).collect(Collectors.toList());
    }

}
