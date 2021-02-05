package com.paulocandido.model;

import com.paulocandido.model.moon.PointType;

public class Spaceship {

    private static final SpaceshipPoints[] points = {
            new SpaceshipPoints(-0.5, 0.5, true),
            new SpaceshipPoints(0, 0.5, true),
            new SpaceshipPoints(0.5, 0.5, true),
            new SpaceshipPoints(0.44, 0.19, false),
            new SpaceshipPoints(0.28, -0.17, false),
            new SpaceshipPoints(0.33, -0.44, false),
            new SpaceshipPoints(0, -0.5, false),
            new SpaceshipPoints(-0.20, -0.49, false),
            new SpaceshipPoints(-0.28, -0.17, false),
            new SpaceshipPoints(-0.44, 0.19, false)
    };
    private static final SpaceshipPoints distancePoint = points[1];

    public static final double WIDTH = 74;
    public static final double HEIGHT = 64;

    private Status status;
    private double x;
    private double y;
    private double r;

    public Spaceship(double x, double y, double r) {
        this.status = Status.active;
        this.x = x;
        this.y = y;
        this.r = r;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getR() {
        return r;
    }

    public Status getStatus() {
        return status;
    }

    public void update(Moon moon) {
        this.y += 1;

        SpaceshipPoints.Calculated[] points = getPoints();

        for (SpaceshipPoints.Calculated point : points) {
            int px = point.getIntX();
            int py = point.getIntY();

            if (moon.getType(px, py) != PointType.air) {
                status = Status.fail;
            }
        }
    }

    public SpaceshipPoints.Calculated[] getPoints() {
        var calculated = new SpaceshipPoints.Calculated[points.length];
        for (int i = 0; i < points.length; i++) {
            calculated[i] = points[i].calculate(this);
        }
        return calculated;
    }

    public SpaceshipPoints.Calculated getDistancePoint() {
        return distancePoint.calculate(this);
    }

    public enum Status {
        active, fail, success
    }
}
