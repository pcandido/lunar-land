package com.paulocandido.ui;

import com.paulocandido.model.Moon;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;

public class UI {

    private Canvas canvas;
    private final int width;
    private final int height;

    public UI(Moon moon) {

        this.width = moon.getWidth();
        this.height = moon.getHeight();

        JFrame frame = new JFrame("Dino");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {

            }

            @Override
            public void windowClosed(WindowEvent e) {
                System.exit(1);
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
        });

        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(this.width, this.height));
        frame.getContentPane().add(canvas);
        frame.pack();

        canvas.setImage(draw(moon));

        frame.setVisible(true);
    }

    private Image draw(Moon moon) {
        BufferedImage img = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_ARGB);
        int[][] dists = moon.getDistances();

        int max = 0;

        for (int[] distX : dists) {
            for (int dist : distX) {
                max = Math.max(max, dist);
            }
        }

        for (int i = 0; i < dists.length; i++) {
            for (int j = 0; j < dists[0].length; j++) {
                if (dists[i][j] < 0)
                    img.setRGB(i, j, Color.red.getRGB());
                else
                    img.setRGB(i, j, new Color(0, 0, (int) (dists[i][j] * 255.0 / max)).getRGB());
            }
        }

        return img;
    }

}
