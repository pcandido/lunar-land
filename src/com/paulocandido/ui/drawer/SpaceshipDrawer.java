package com.paulocandido.ui.drawer;

import com.paulocandido.model.Spaceship;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class SpaceshipDrawer {

    private Image spaceshipImage;
    private Image fireImage;

    public SpaceshipDrawer() throws IOException {
        this.spaceshipImage = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("spaceship.png")));
        this.fireImage = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("fire.png")));
    }

    private int toInt(double value) {
        return (int) Math.round(value);
    }

    public void draw(Graphics2D canvas, Spaceship spaceship) {
        canvas.rotate(Math.toRadians(spaceship.getR()), spaceship.getX(), spaceship.getY());

        canvas.drawImage(
                spaceshipImage,
                toInt(spaceship.getX() - Spaceship.WIDTH / 2),
                toInt(spaceship.getY() - Spaceship.HEIGHT / 2),
                toInt(spaceship.getX() + Spaceship.WIDTH / 2),
                toInt(spaceship.getY() + Spaceship.HEIGHT / 2),
                0,
                0,
                spaceshipImage.getWidth(null),
                spaceshipImage.getHeight(null),
                null
        );

        canvas.rotate(Math.toRadians(spaceship.getR() * -1), spaceship.getX(), spaceship.getY());
    }

}
