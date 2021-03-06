package com.paulocandido.ui;

import com.paulocandido.model.Moon;
import com.paulocandido.model.Population;
import com.paulocandido.ui.drawer.MoonDrawer;
import com.paulocandido.ui.drawer.SpaceshipDrawer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class UI implements Runnable, WindowListener {

    private final Canvas canvas;
    private final int width;
    private final int height;

    private final MoonDrawer moonDrawer;
    private final SpaceshipDrawer spaceshipDrawer;

    public UI(Moon moon, Population population, VelocityListener velocityListener) throws IOException {

        this.width = moon.getWidth();
        this.height = moon.getHeight();

        JFrame frame = new JFrame("Lunar Land");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.addWindowListener(this);
        frame.setLayout(new BorderLayout());

        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(this.width, this.height));
        frame.getContentPane().add(canvas, BorderLayout.NORTH);

        var velocity = new JSlider(0, 10, 0);
        frame.getContentPane().add(velocity, BorderLayout.SOUTH);
        velocity.addChangeListener(e -> velocityListener.onVelocityChange(velocity.getValue()));

        frame.pack();

        spaceshipDrawer = new SpaceshipDrawer(population, moon, this.width, this.height);
        moonDrawer = new MoonDrawer(moon, this.width, this.height);

        new Thread(this).start();

        frame.setVisible(true);
    }

    @Override
    public void run() {
        while (true) {

            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D graphics = (Graphics2D) image.getGraphics();

            moonDrawer.draw(graphics);
            spaceshipDrawer.draw(graphics);

            this.canvas.setImage(image);

            try {
                //noinspection BusyWait
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {

    }

    @Override
    public void windowClosed(WindowEvent e) {
        System.exit(0);
    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }

    public interface VelocityListener {
        void onVelocityChange(int velocity);
    }

}
