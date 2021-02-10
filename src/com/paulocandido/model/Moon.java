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
    private final int initialDistance;

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
        this.initialDistance = distances[startX][startY];
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

    public int getInitialDistance(){
        return initialDistance;
    }

    public PointType getType(int x, int y) {
        if (x < 0) return PointType.out;
        if (y < 0) return PointType.out;
        if (x >= width) return PointType.out;
        if (y >= height) return PointType.out;
        return types[x][y];
    }

    public double[] getObstacles(int x, int y) {
        double[] result = new double[8];

        //N
        for (int iy = y; iy > 0; iy--) {
            if (types[x][iy] == PointType.air) result[0]++;
            else break;
        }

        //NE
        for (int ix = x, iy = y; ix < width && iy > 0; ix++, iy--) {
            if (types[ix][iy] == PointType.air) result[1]++;
            else break;
        }

        //E
        for (int ix = x; ix < width; ix++) {
            if (types[ix][y] == PointType.air) result[2]++;
            else break;
        }

        //SE
        for (int ix = x, iy = y; ix < width && iy < height; ix++, iy++) {
            if (types[ix][iy] == PointType.air) result[3]++;
            else break;
        }

        //S
        for (int iy = y; iy < height; iy++) {
            if (types[x][iy] == PointType.air) result[4]++;
            else break;
        }

        //SW
        for (int ix = x, iy = y; ix > 0 && iy < height; ix--, iy++) {
            if (types[ix][iy] == PointType.air) result[5]++;
            else break;
        }

        //W
        for (int ix = x; ix > 0; ix--) {
            if (types[ix][y] == PointType.air) result[6]++;
            else break;
        }

        //NW
        for (int ix = x, iy = y; ix > 0 && iy > 0; ix--, iy--) {
            if (types[ix][iy] == PointType.air) result[7]++;
            else break;
        }

        return result;
    }

    public double getDistance(int x, int y) {
        return this.distances[x][y];
    }

}
