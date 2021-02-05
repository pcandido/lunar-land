package com.paulocandido;

import com.paulocandido.model.Moon;
import com.paulocandido.model.Population;
import com.paulocandido.ui.UI;

import java.io.IOException;

public class Main extends Thread {

    public static void main(String[] args) throws IOException {
        new Main("moon1.png", 50, 1).start();
    }

    private Moon moon;
    private Population population;

    public Main(String moonFileName, int populationSize, double startingNoise) throws IOException {
        this.moon = new Moon(moonFileName);
        this.population = new Population(populationSize, startingNoise);
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
