package com.paulocandido.model;

public class SpaceshipPoints {

    private final double xDelta;
    private final double yDelta;
    private final boolean landingGear;

    public SpaceshipPoints(double xDelta, double yDelta, boolean landingGear) {
        this.xDelta = xDelta;
        this.yDelta = yDelta;
        this.landingGear = landingGear;
    }

    public double getxDelta() {
        return xDelta;
    }

    public double getyDelta() {
        return yDelta;
    }

    public Calculated calculate(Spaceship spaceship) {
        var rRad = Math.toRadians(360-spaceship.getR());
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

        public boolean isLandingGear() {
            return landingGear;
        }
    }
}
