package com.paulocandido;

import com.paulocandido.model.Moon;
import com.paulocandido.model.Population;
import com.paulocandido.ui.UI;

import java.io.IOException;

public class Main extends Thread {

    public static void main(String[] args) throws IOException {
        var mapName = args[0];
        var trainedDir = args[1];
        var trainedFile = trainedDir + "/" + mapName.split("\\.")[0] + ".ll";

        var moon = new Moon(mapName, 0.003, 0.001);
        var population = new Population(moon, 50, 1, 1500, trainedFile);
        new Main(moon, population).start();
    }

    private Moon moon;
    private Population population;
    private int velocity = 0;

    public Main(Moon moon, Population population) throws IOException {
        this.moon = moon;
        this.population = population;
        new UI(this.moon, this.population, a -> velocity = a);
    }

    @Override
    @SuppressWarnings("BusyWait")
    public void run() {
        while (true) {
            while (population.hasAnyActive()) {
                population.update(moon);

                try {
                    Thread.sleep(10 - velocity);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    System.exit(1);
                }
            }

            if (population.hasAnySuccess()) {
                try {
                    Thread.sleep((10 - velocity) * 1000 / 2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    System.exit(1);
                }
            }

            population.nextGeneration(moon);
        }
    }

}