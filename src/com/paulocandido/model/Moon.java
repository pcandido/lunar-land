package com.paulocandido.model;

import com.paulocandido.model.moon.PointType;
import com.paulocandido.model.moon.StartScanner;
import com.paulocandido.model.moon.TypeScanner;
import com.paulocandido.model.moon.WavefrontScanner;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class Moon {

    private final URL imageFile;
    private final int width;
    private final int height;
    private final double gravity;
    private final double friction;
    private final int startX;
    private final int startY;
    private final PointType[][] types;
    private final int[][] distances;
    private final int maxDistance;

    public Moon(String imageFileName, double gravity, double friction) throws IOException {
        this.imageFile = Objects.requireNonNull(getClass().getClassLoader().getResource(imageFileName));

        var image = ImageIO.read(this.imageFile);

        this.width = image.getWidth();
        this.height = image.getHeight();

        this.gravity = gravity;
        this.friction = friction;

        this.types = new TypeScanner().scan(image);
        this.distances = new WavefrontScanner().getDistances(this.types);
        var start = new StartScanner().scan(image);
        this.startX = start.getX();
        this.startY = start.getY();

        int maxDistance = 0;
        for (int[] yDistances : distances) {
            for (int distance : yDistances) {
                maxDistance = Math.max(maxDistance, distance);
            }
        }
        this.maxDistance = maxDistance;
    }

    public URL getImageFile() {
        return imageFile;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getStartX() {
        return startX;
    }

    public int getStartY() {
        return startY;
    }

    public double getGravity() {
        return gravity;
    }

    public double getFriction() {
        return friction;
    }

    public int getMaxDistance() {
        return maxDistance;
    }

    public PointType getType(int x, int y) {
        if (x < 0) return PointType.out;
        if (y < 0) return PointType.out;
        if (x >= width) return PointType.out;
        if (y >= height) return PointType.out;
        return types[x][y];
    }

    public double getDistance(int x, int y) {
        return this.distances[x][y];
    }

}
