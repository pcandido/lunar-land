package com.paulocandido.ui.drawer;

import com.paulocandido.model.Population;
import com.paulocandido.model.Spaceship;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class SpaceshipDrawer {

    private Population population;
    private Image spaceshipRedImage;
    private Image spaceshipBlackImage;
    private Image spaceshipTransparentImage;
    private Image fireImage;

    public SpaceshipDrawer(Population population) throws IOException {
        this.population = population;
        this.spaceshipRedImage = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("spaceship-red.png")));
        this.spaceshipBlackImage = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("spaceship-black.png")));
        this.spaceshipTransparentImage = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("spaceship-transp.png")));
        this.fireImage = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("fire.png")));
    }

    private int toInt(double value) {
        return (int) Math.round(value);
    }

    public void draw(Graphics2D canvas) {
        for (Spaceship spaceship : population.getSnapshot()) {
            drawSpaceship(canvas, spaceship, SpaceshipColor.transparent);
        }
        if (population.getLastBest() != null)
            drawSpaceship(canvas, population.getLastBest(), SpaceshipColor.red);
    }

    private void drawSpaceship(Graphics2D canvas, Spaceship spaceship, SpaceshipColor color) {
        if (spaceship.getStatus() == Spaceship.Status.fail) return;

        double x = spaceship.getX();
        double y = spaceship.getY();
        double r = spaceship.getR();

        Image spaceshipImage = switch (color) {
            case red -> spaceshipRedImage;
            case black -> spaceshipBlackImage;
            case transparent -> spaceshipTransparentImage;
        };

        canvas.rotate(Math.toRadians(r), x, y);

        canvas.drawImage(
                spaceshipImage,
                toInt(x - Spaceship.WIDTH / 2),
                toInt(y - Spaceship.HEIGHT / 2),
                toInt(x + Spaceship.WIDTH / 2),
                toInt(y + Spaceship.HEIGHT / 2),
                0,
                0,
                spaceshipImage.getWidth(null),
                spaceshipImage.getHeight(null),
                null
        );

        if (spaceship.isJetting()) {
            canvas.drawImage(
                    fireImage,
                    toInt(x - Spaceship.WIDTH * 0.2),
                    toInt(y + Spaceship.HEIGHT / 2),
                    toInt(x + Spaceship.WIDTH * 0.2),
                    toInt(y + Spaceship.HEIGHT),
                    0,
                    0,
                    fireImage.getWidth(null),
                    fireImage.getHeight(null),
                    null
            );
        }

        canvas.rotate(Math.toRadians(r * -1), x, y);
    }

    private enum SpaceshipColor {
        red, black, transparent
    }

}
