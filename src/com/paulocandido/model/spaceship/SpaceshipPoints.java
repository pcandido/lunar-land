package com.paulocandido.model.spaceship;

import com.paulocandido.model.Spaceship;

public class SpaceshipPoints {

    private final double xDelta;
    private final double yDelta;
    private final boolean landingGear;

    public SpaceshipPoints(double xDelta, double yDelta, boolean landingGear) {
        this.xDelta = xDelta;
        this.yDelta = yDelta;
        this.landingGear = landingGear;
    }

    public Calculated calculate(Spaceship spaceship) {
        var rRad = Math.toRadians(360 - spaceship.getR());
        var x = Spaceship.WIDTH * xDelta;
        var y = Spaceship.HEIGHT * yDelta;

        var rx = x * Math.cos(rRad) + y * Math.sin(rRad);
        var ry = -x * Math.sin(rRad) + y * Math.cos(rRad);

        return new Calculated(
                spaceship.getX() + rx,
                spaceship.getY() + ry
        );
    }

    public class Calculated {
        private final double x;
        private final double y;

        public Calculated(double x, double y) {
            this.x = x;
            this.y = y;
        }

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }

        public int getIntX() {
            return (int) x;
        }

        public int getIntY() {
            return (int) y;
        }

        public boolean isLandingGear() {
            return landingGear;
        }
    }
}
