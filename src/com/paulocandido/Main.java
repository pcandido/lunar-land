package com.paulocandido;

import com.paulocandido.model.Moon;
import com.paulocandido.model.Population;
import com.paulocandido.ui.UI;

import java.io.IOException;

public class Main extends Thread {

    public static void main(String[] args) throws IOException {
        new Main(
                new Moon(
                        "moon1.png",
                        0.003,
                        0.001
                ),
                new Population(
                        50,
                        1,
                        1500
                )
        ).start();
    }

    private Moon moon;
    private Population population;

    public Main(Moon moon, Population population) throws IOException {
        this.moon = moon;
        this.population = population;
        new UI(this.moon, this.population);
    }

    @Override
    public void run() {
        while (true) {
            this.population.update(moon);

            try {
                //noinspection BusyWait
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
    }
}
