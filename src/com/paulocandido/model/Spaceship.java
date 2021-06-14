package com.paulocandido.model;

import com.paulocandido.ia.NeuralNetwork;
import com.paulocandido.model.moon.Obstacle;
import com.paulocandido.model.moon.PointType;
import com.paulocandido.model.spaceship.SpaceshipDirectionCalculator;
import com.paulocandido.model.spaceship.SpaceshipPoint;
import com.paulocandido.model.spaceship.SpaceshipPointCalculator;

public class Spaceship {

    public static final double WIDTH = 74;
    public static final double HEIGHT = 64;

    public static final double MAX_XY_VELOCITY = 3;
    public static final double MAX_R_VELOCITY = 2;

    private static final int ANN_INPUT_SIZE = 14;
    private static final int ANN_OUTPUT_SIZE = 3;

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
    private SpaceshipPoint[] points;
    private Obstacle[] obstacles;
    NeuralNetwork neuralNetwork;
    SpaceshipDirectionCalculator directionCalculator;

    public Spaceship(Moon moon) {
        this(moon, new NeuralNetwork(ANN_INPUT_SIZE, ANN_OUTPUT_SIZE, 6, 6));
    }

    public Spaceship(Moon moon, NeuralNetwork neuralNetwork) {
        this.status = Status.active;
        this.x = moon.getStartX();
        this.y = moon.getStartY();
        this.r = 0;
        this.vx = 0;
        this.vy = 0;
        this.vr = 0;
        this.jet = false;
        this.fuel = 5000;
        this.fitness = 0;
        this.points = SpaceshipPointCalculator.aloc();
        this.obstacles = moon.getObstacleCalculator().aloc();
        this.neuralNetwork = neuralNetwork;

        directionCalculator = new SpaceshipDirectionCalculator(this, moon);
        SpaceshipPointCalculator.calculate(this);
        moon.getObstacleCalculator().calculate(this);
    }

    public Spaceship clone(Moon moon) {
        return new Spaceship(moon, this.neuralNetwork.clone());
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

    public double getFitness() {
        return fitness;
    }

    public Obstacle[] getObstacles() {
        return obstacles;
    }

    public SpaceshipPoint[] getPoints() {
        return points;
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

    private boolean[] think(Moon moon) {

        var input = new double[ANN_INPUT_SIZE];
        var i = 0;

        input[i++] = vx;
        input[i++] = vy;
        input[i++] = vr;
        input[i++] = getRNorm();
        input[i++] = directionCalculator.calculate();
        input[i++] = dist;
        for (var obstacle : obstacles) {
            input[i++] = obstacle.dist();
        }

        double[] result = this.neuralNetwork.calculate(input);

        return new boolean[]{
                result[0] > 0,
                result[1] > 0,
                result[2] > 0
        };
    }

    public void update(Moon moon) {
        if (status != Status.active) return;

        vy += moon.getGravity();
        vx *= 1 - moon.getFriction();
        vr *= 1 - moon.getFriction();

        jet = false;
        boolean[] thought = think(moon);

        if (thought[0] && fuel >= 1) {
            //main
            this.vx += Math.cos(Math.toRadians(this.r - 90)) * 0.006;
            this.vy += Math.sin(Math.toRadians(this.r - 90)) * 0.006;
            this.jet = true;
            this.fuel -= 1;
        }
        if (thought[1] && fuel >= 0.1) {
            //right
            this.vr += 0.006;
            this.fuel -= 0.1;
        }
        if (thought[2] && fuel >= 0.1) {
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

        SpaceshipPointCalculator.calculate(this);
        moon.getObstacleCalculator().calculate(this);

        var touchedStation = false;

        for (var point : points) {
            int px = point.intX();
            int py = point.intY();

            switch (moon.getType(px, py)) {
                case air -> {
                }
                case out, ground -> status = Status.fail;
                case station -> {
                    if (point.landingGear())
                        touchedStation = true;
                    else
                        status = Status.fail;
                }
            }

            if (moon.getType(px, py) != PointType.air) {
                status = Status.fail;
            }

            if (touchedStation) {
                if (r > 3 && r < 357) status = Status.fail;
                else if (vx > 0.1 || vy > 0.1 || vr > 0.1) status = Status.fail;
                else status = Status.success;
            }

            if (status != Status.active) {
                jet = false;
            }

            this.fitness = calcFitness(moon);
        }
    }

    private double calcFitness(Moon moon) {
        var distNorm = Math.abs(dist / moon.getInitialDistance());
        var rNorm = getRNorm();
        var vxNorm = Math.abs(vx / MAX_XY_VELOCITY);
        var vyNorm = Math.abs(vy / MAX_XY_VELOCITY);
        var vrNorm = Math.abs(vr / MAX_R_VELOCITY);

        var value = ((1 - distNorm) * 4) + (1 - rNorm) + (1 - vxNorm) + (1 - vyNorm) + (1 - vrNorm);

        if (status == Status.success) value += 5;

        return value;
    }

    public enum Status {
        active, fail, success
    }
}
