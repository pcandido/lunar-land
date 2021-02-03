package com.paulocandido.ui.drawer;

import com.paulocandido.model.Moon;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class MoonDrawer {

    private final Image image;
    private final int width;
    private final int height;

    public MoonDrawer(Moon moon, int width, int height) throws IOException {
        this.image = ImageIO.read(moon.getImageFile());
        this.width = width;
        this.height = height;
    }

    public void draw(Graphics2D canvas) {
        canvas.drawImage(
                image,
                0,
                0,
                this.width,
                this.height,
                0,
                0,
                image.getWidth(null),
                image.getHeight(null),
                null
        );
    }

}
