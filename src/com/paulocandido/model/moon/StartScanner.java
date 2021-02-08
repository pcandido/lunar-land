package com.paulocandido.model.moon;

import java.awt.*;
import java.awt.image.BufferedImage;

public class StartScanner {

    public Point scan(BufferedImage image) {
        Point start = null;

        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                var color = new Color(image.getRGB(i, j));
                if (color.equals(Color.blue)) {
                    if (start == null) {
                        start = new Point(i, j);
                    } else {
                        throw new RuntimeException("O Mapa deve ter exatamente um start point");
                    }
                }
            }
        }

        if (start == null)
            throw new RuntimeException("O Mapa deve ter exatamente um start point");


        return start;
    }

    public static class Point {
        private final int x;
        private final int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }
    }
}
