package agh.ics.oop;

import agh.ics.oop.model.*;
import agh.ics.oop.model.util.MapSettings;
import com.sun.scenario.Settings;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Simulation implements  Runnable {
    private List<Animal> animals = new ArrayList<>();
    private final WorldMap map;
    private final MapSettings settings;

    public Simulation(List<Vector2d> positions, WorldMap map, int genomeLength, MapSettings settings) {
        this.map = map;
        this.settings = settings;

        int grassInJungle = (int) (Math.ceil(settings.getInitialGrassCount() * 0.8f));
        int grassOnSteppes = (int) (Math.floor(settings.getInitialGrassCount() * 0.2f));

        RandomPositionGenerator jungleGen = new RandomPositionGenerator(map.getJungleBoundary(), grassInJungle);
        jungleGen.forEach(pos -> map.place(new Grass(pos)));

        RandomPositionGenerator northGen = new RandomPositionGenerator(map.getNorthSteppes(), (int) Math.ceil(grassOnSteppes / 2.0f));
        northGen.forEach(pos -> map.place(new Grass(pos)));

        RandomPositionGenerator southGen = new RandomPositionGenerator(map.getSouthSteppes(), (int) Math.floor(grassOnSteppes / 2.0f));
        southGen.forEach(pos -> map.place(new Grass(pos)));

        for (Vector2d position : positions) {
            Genotype genes = Genotype.generateRandom(genomeLength);
            Animal animal = new Animal(position, genes);
            map.place(animal);
            animals.add(animal);
        }
    }

    public List<Animal> getAnimals() {
        return animals;
    }

    @Override
    public void run() {
        // 1 usunięcie martwych zwierzaków
        // zwierzeta trzeba jeszcze z mapy usunąć
        animals = animals.stream()
                .filter(animal -> !animal.hasStarved())
                .collect(Collectors.toList());

        // ruch wszystkich zwierzaków
        // bedzie trzeba to zamienic na while True. Na razie dam tutaj po
        // prostu jakas stala.
        for (int i = 0; i < 30; i++) { // ten for powinien obejmowac wszystkie metody,
            // ale dopoki ich nie ma to rusza tylko zwierzakami.
            for (Animal animal : animals) {
                map.move(animal);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }

//     3 zjadzanie roślin
//     aktualizowanie energi
//
//     usunięcie rośliny
//
//
//     4 rozmnażanie
//     aktualizowanie energi
//
//     5 wzrost nowych roślin
//
//     6 utrata energi zewzględu na przeminięcie dnia
    }
}
