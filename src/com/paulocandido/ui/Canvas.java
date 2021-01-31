package com.paulocandido.ui;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Image;

public class Canvas extends JPanel {

    private Image image;

    public void setImage(Image image) {
        this.image = image;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (image != null) {
            g.drawImage(
                    this.image,
                    0,
                    0,
                    this.getWidth(),
                    this.getHeight(),
                    0,
                    0,
                    image.getWidth(null),
                    image.getHeight(null),
                    null
            );
        }
    }
}
