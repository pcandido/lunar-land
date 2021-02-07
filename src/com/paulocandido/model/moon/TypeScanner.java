package com.paulocandido.model.moon;

import java.awt.*;
import java.awt.image.BufferedImage;

public class TypeScanner {

    public PointType[][] scan(BufferedImage image) {
        var touchPoint = false;
        var types = new PointType[image.getWidth()][image.getHeight()];

        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                var color = new Color(image.getRGB(i, j));
                if (color.equals(Color.black)) {
                    types[i][j] = PointType.ground;
                } else if (color.equals(Color.white)) {
                    types[i][j] = PointType.air;
                } else if (color.equals(Color.green)) {
                    types[i][j] = PointType.station;
                } else if (color.equals(Color.magenta)) {
                    if (touchPoint)
                        throw new RuntimeException("Só é permitido um touch point por mapa");
                    touchPoint = true;
                    types[i][j] = PointType.touch_point;
                } else {
                    throw new RuntimeException("Cor desconhecida no mapa: " + color.toString());
                }
            }
        }

        if (!touchPoint)
            throw new RuntimeException("Nenhum touch point encontrado");

        return types;
    }

}
