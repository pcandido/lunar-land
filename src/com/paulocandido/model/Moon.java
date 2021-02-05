package com.paulocandido.model;

import com.paulocandido.utils.Wavefront;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class Moon {

    private final URL imageFile;
    private final int width;
    private final int height;
    private final Type[][] types;
    private final int[][] distances;

    public Moon(String imageFileName) throws IOException {
        this.imageFile = Objects.requireNonNull(getClass().getClassLoader().getResource(imageFileName));

        var image = ImageIO.read(this.imageFile);

        this.width = image.getWidth();
        this.height = image.getHeight();

        this.types = fillTypes(image);
        this.distances = new Wavefront().getDistances(this.types);
    }

    private static Type[][] fillTypes(BufferedImage image) {
        var types = new Type[image.getWidth()][image.getHeight()];

        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                var color = new Color(image.getRGB(i, j));
                if (color.equals(Color.black)) {
                    types[i][j] = Type.ground;
                } else if (color.equals(Color.white)) {
                    types[i][j] = Type.air;
                } else if (color.equals(Color.green)) {
                    types[i][j] = Type.station;
                } else {
                    throw new RuntimeException("Cor desconhecida no mapa: " + color.toString());
                }
            }
        }

        return types;
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

    public Type[][] getTypes() {
        return types;
    }

    public Type getType(int x, int y) {
        if (x < 0) return Type.out;
        if (y < 0) return Type.out;
        if (x >= width) return Type.out;
        if (y >= height) return Type.out;
        return types[x][y];
    }

    public int[][] getDistances() {
        return distances;
    }

    public enum Type {
        out, ground, air, station
    }
}
