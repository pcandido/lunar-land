package com.paulocandido.model;

import com.paulocandido.model.moon.PointType;
import com.paulocandido.model.moon.TypeScanner;
import com.paulocandido.model.moon.WavefrontScanner;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class Moon {

    private final URL imageFile;
    private final int width;
    private final int height;
    private final PointType[][] types;
    private final int[][] distances;

    public Moon(String imageFileName) throws IOException {
        this.imageFile = Objects.requireNonNull(getClass().getClassLoader().getResource(imageFileName));

        var image = ImageIO.read(this.imageFile);

        this.width = image.getWidth();
        this.height = image.getHeight();

        this.types = new TypeScanner().scan(image);
        this.distances = new WavefrontScanner().getDistances(this.types);
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

    public PointType getType(int x, int y) {
        if (x < 0) return PointType.out;
        if (y < 0) return PointType.out;
        if (x >= width) return PointType.out;
        if (y >= height) return PointType.out;
        return types[x][y];
    }

    public int[][] getDistances() {
        return distances;
    }


}
