package com.paulocandido.ui.drawer;

import com.paulocandido.model.Population;
import com.paulocandido.model.Spaceship;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class SpaceshipDrawer {

    private Population population;
    private Image spaceshipImage;
    private Image fireImage;

    public SpaceshipDrawer(Population population) throws IOException {
        this.population = population;
        this.spaceshipImage = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("spaceship.png")));
        this.fireImage = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("fire.png")));
    }

    private int toInt(double value) {
        return (int) Math.round(value);
    }

    public void draw(Graphics2D canvas) {
        for (Spaceship spaceship : population.getSnapshot()) {
            if (spaceship.getStatus() == Spaceship.Status.fail) continue;

            double x = spaceship.getX();
            double y = spaceship.getY();
            double r = spaceship.getR();

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
    }

}
