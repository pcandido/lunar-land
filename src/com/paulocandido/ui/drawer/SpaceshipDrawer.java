package com.paulocandido.ui.drawer;

import com.paulocandido.model.Moon;
import com.paulocandido.model.Population;
import com.paulocandido.model.Spaceship;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class SpaceshipDrawer {

    private final Population population;
    private final Moon moon;
    private final int width;
    private final int height;

    private final Image spaceshipRedImage;
    private final Image spaceshipBlackImage;
    private final Image spaceshipTransparentImage;
    private final Image fireBlackImage;
    private final Image fireRedImage;
    private final Image fireTransparentImage;

    public SpaceshipDrawer(Population population, Moon moon, int width, int height) throws IOException {
        this.population = population;
        this.moon = moon;
        this.width = width;
        this.height = height;

        this.spaceshipRedImage = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("spaceship-red.png")));
        this.spaceshipBlackImage = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("spaceship-black.png")));
        this.spaceshipTransparentImage = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("spaceship-transp.png")));
        this.fireBlackImage = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("fire-black.png")));
        this.fireRedImage = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("fire-red.png")));
        this.fireTransparentImage = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("fire-transp.png")));
    }

    private int toInt(double value) {
        return (int) Math.round(value);
    }

    public void draw(Graphics2D canvas) {
        for (Spaceship spaceship : population.getSnapshot()) {
            drawSpaceship(canvas, spaceship, SpaceshipColor.transparent, false);
        }

        if (population.getLastBest() != null)
            drawSpaceship(canvas, population.getLastBest(), SpaceshipColor.red, true);
    }

    private void drawObstacle(Graphics2D canvas, Spaceship spaceship, double x, double y) {
        canvas.drawLine(
                toInt(spaceship.getX()),
                toInt(spaceship.getY()),
                toInt(x),
                toInt(y)
        );

        canvas.fillOval(
                toInt(x) - 3,
                toInt(y) - 3,
                6,
                6
        );
    }

    private void drawSpaceship(Graphics2D canvas, Spaceship spaceship, SpaceshipColor color, boolean drawObstacles) {
        if (spaceship.getStatus() == Spaceship.Status.fail) return;

        double x = spaceship.getX();
        double y = spaceship.getY();
        double r = spaceship.getR();

        var spaceshipImage = switch (color) {
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
            var fireImage = switch (color) {
                case black -> fireBlackImage;
                case red -> fireRedImage;
                case transparent -> fireTransparentImage;
            };

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

        if (drawObstacles) {
            canvas.setColor(Color.cyan);
            var obstacles = moon.getObstacles((int) spaceship.getX(), (int) spaceship.getY());

            drawObstacle(canvas, spaceship, spaceship.getX(), spaceship.getY() - obstacles[0]);
            drawObstacle(canvas, spaceship, spaceship.getX() + obstacles[1], spaceship.getY() - obstacles[1]);
            drawObstacle(canvas, spaceship, spaceship.getX() + obstacles[2], spaceship.getY());
            drawObstacle(canvas, spaceship, spaceship.getX() + obstacles[3], spaceship.getY() + obstacles[3]);
            drawObstacle(canvas, spaceship, spaceship.getX(), spaceship.getY() + obstacles[4]);
            drawObstacle(canvas, spaceship, spaceship.getX() - obstacles[5], spaceship.getY() + obstacles[5]);
            drawObstacle(canvas, spaceship, spaceship.getX() - obstacles[6], spaceship.getY());
            drawObstacle(canvas, spaceship, spaceship.getX() - obstacles[7], spaceship.getY() - obstacles[7]);

        }
    }

    private enum SpaceshipColor {
        red, black, transparent
    }

}
