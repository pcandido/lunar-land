package com.paulocandido.model.spaceship;

public record SpaceshipPoint(double x, double y, boolean landingGear) {

    public int intX() {
        return (int) x;
    }

    public int intY() {
        return (int) y;
    }

}