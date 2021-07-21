package com.paulocandido;

import com.paulocandido.model.Moon;
import com.paulocandido.model.Population;
import com.paulocandido.model.Spaceship;
import com.paulocandido.ui.UI;

import java.io.IOException;

public class Main extends Thread {

    public static void main(String[] args) throws IOException {
        var mapName = args[0];
        var trainedDir = args[1];
        var trainedFile = trainedDir + "/" + mapName.split("\\.")[0] + ".ll";

        var moon = new Moon(mapName, 0.003, 0.001);
        var population = new Population(moon, trainedFile);
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
            var start = System.currentTimeMillis();
            while (population.hasAnyActive()) {
                population.update(moon);

                try {
                    Thread.sleep(10 - velocity);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    System.exit(1);
                }
            }
            var end = System.currentTimeMillis();

            if (population.hasAnySuccess()) {
                try {
                    Thread.sleep((10 - velocity) * 1000 / 2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    System.exit(1);
                }
            }

            var best = population.getBest();
            System.out.printf("%4d :: %3.6f :: %4.2f :: %6d :: %s\n", population.getGeneration(), best.getFitness(), best.getFuel(), end - start, best.getStatus() == Spaceship.Status.success ? "SUCCESS" : "FAIL");

            population.nextGeneration(moon);
        }
    }

}