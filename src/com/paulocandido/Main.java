package com.paulocandido;

import com.paulocandido.model.Moon;
import com.paulocandido.model.Population;
import com.paulocandido.ui.UI;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        var moon = new Moon("map.png");
        var population = new Population(50, 2);
        new UI(moon,population );

    }

}
