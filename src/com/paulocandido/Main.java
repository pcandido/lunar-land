package com.paulocandido;

import com.paulocandido.model.Moon;
import com.paulocandido.model.Population;
import com.paulocandido.ui.UI;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        var moon = new Moon("map.png");
        var population = new Population(50, 1);
        new UI(moon,population );

        new Thread(() -> {
            while (true) {
                population.update(moon);

                try {
                    //noinspection BusyWait
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    System.exit(1);
                }
            }
        }).start();

    }

}
