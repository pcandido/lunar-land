package com.paulocandido.model;

import com.paulocandido.ia.Layer;
import com.paulocandido.ia.NeuralNetwork;
import com.paulocandido.model.moon.PointType;
import com.paulocandido.model.spaceship.SpaceshipPoints;

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

    private double dist;
    private double fitness;
    NeuralNetwork neuralNetwork;

    public Spaceship() {
        this(new NeuralNetwork(2, 3, 4));
    }

    public Spaceship(NeuralNetwork neuralNetwork) {
        this.status = Status.active;
        this.x = 70;
        this.y = 70;
        this.r = 0;
        this.vx = 0;
        this.vy = 0;
        this.vr = 0;
        this.jet = false;
        this.fuel = 1500;
        this.fitness = 0;
        this.neuralNetwork = neuralNetwork;
    }

    @SuppressWarnings("MethodDoesntCallSuperMethod")
    public Spaceship clone() {
        return new Spaceship(this.neuralNetwork.clone());
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

    public double getRNorm() {
        return 1 - (Math.pow(this.r - 180, 2) / (180 * 180));
    }

    public Status getStatus() {
        return status;
    }

    public boolean isJetting() {
        return jet;
    }

    public double getDist() {
        return dist;
    }

    public double getFitness() {
        return fitness;
    }

    public NeuralNetwork getNeuralNetwork() {
        return neuralNetwork;
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

        double[] result = this.neuralNetwork.calculate(new double[]{
                getR(),
                this.dist
        });

        if (result[0] > 0 && fuel >= 1) {
            //main
            this.vx += Math.cos(Math.toRadians(this.r) - 90) * 0.006;
            this.vy += Math.sin(Math.toRadians(this.r) - 90) * 0.006;
            this.jet = true;
            this.fuel -= 1;
        }
        if (result[1] > 0 && fuel >= 0.1) {
            //right
            this.vr += 0.006;
            this.fuel -= 0.1;
        }
        if (result[2] > 0 && fuel >= 0.1) {
            //left
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

        this.dist = moon.getDistance((int) x, (int) y);

        var distNorm = Math.abs(dist / moon.getMaxDistance());
        var rNorm = getRNorm();
        var vxNorm = Math.abs(vx / MAX_XY_VELOCITY);
        var vyNorm = Math.abs(vy / MAX_XY_VELOCITY);

        this.fitness = (1 - distNorm) * 1.3 + (1 - rNorm) + (1 - vxNorm) + (1 - vyNorm);

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
