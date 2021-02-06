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

    public static final double MAX_XY_VELOCITY = 3;
    public static final double MAX_R_VELOCITY = 2;

    private Status status;

    private double x;
    private double y;
    private double r;

    private double vx;
    private double vy;
    private double vr;

    private boolean jet;
    private double fuel;

    public Spaceship(double x, double y, double r, double fuel) {
        this.status = Status.active;
        this.x = x;
        this.y = y;
        this.r = r;
        this.vx = 0;
        this.vy = 0;
        this.vr = 0;
        this.jet = false;
        this.fuel = fuel;
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

    public double getVx() {
        return vx;
    }

    public double getVy() {
        return vy;
    }

    public double getVr() {
        return vr;
    }

    public Status getStatus() {
        return status;
    }

    public boolean isJetting() {
        return jet;
    }

    private double restrict(double value, double max) {
        if (Math.abs(value) > max) {
            return max * (value / Math.abs(value));
        } else {
            return value;
        }
    }

    public void update(Moon moon) {
        if (status != Status.active) return;

        vy += moon.getGravity();
        vx *= 1 - moon.getFriction();
        vr *= 1 - moon.getFriction();

        jet = false;

        //action

        if (true && fuel >= 1) {//main
            this.vx += Math.cos(Math.toRadians(this.r) - 90) * 0.006;
            this.vy += Math.sin(Math.toRadians(this.r) - 90) * 0.006;
            this.jet = true;
            this.fuel -= 1;
        }
        if (false && fuel >= 0.1) {//right
            this.vr += 0.006;
            this.fuel -= 0.1;
        }
        if (false && fuel >= 0.1) {//left
            this.vr -= 0.006;
            this.fuel -= 0.1;
        }

        if (this.r > 360) this.r -= 360;
        if (this.r < 0) this.r += 360;

        this.vx = this.restrict(this.vx, MAX_XY_VELOCITY);
        this.vy = this.restrict(this.vy, MAX_XY_VELOCITY);
        this.vr = this.restrict(this.vr, MAX_R_VELOCITY);

        this.y += this.vy;
        this.x += this.vx;
        this.r += this.vr;

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
