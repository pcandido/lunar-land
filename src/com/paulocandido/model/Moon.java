package com.paulocandido.model;

import com.paulocandido.utils.Wavefront;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class Moon {

    public static final short TYPE_GROUND = 0;
    public static final short TYPE_AIR = 1;
    public static final short TYPE_SPOT = 2;

    private final URL imageFile;
    private final int width;
    private final int height;
    private final short[][] types;
    private final int[][] distances;

    public Moon(String imageFileName) throws IOException {
        this.imageFile = Objects.requireNonNull(getClass().getClassLoader().getResource(imageFileName));

        var image = ImageIO.read(this.imageFile);

        this.width = image.getWidth();
        this.height = image.getHeight();

        this.types = fillTypes(image);
        this.distances = new Wavefront(TYPE_GROUND, TYPE_AIR, TYPE_SPOT).getDistances(this.types);
    }

    private static short[][] fillTypes(BufferedImage image) {
        var types = new short[image.getWidth()][image.getHeight()];

        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                var color = new Color(image.getRGB(i, j));
                if (color.equals(Color.black)) {
                    types[i][j] = TYPE_GROUND;
                } else if (color.equals(Color.white)) {
                    types[i][j] = TYPE_AIR;
                } else if (color.equals(Color.green)) {
                    types[i][j] = TYPE_SPOT;
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

    public short[][] getTypes() {
        return types;
    }

    public int[][] getDistances() {
        return distances;
    }
}
