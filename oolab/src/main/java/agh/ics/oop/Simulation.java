package agh.ics.oop;

import agh.ics.oop.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Simulation implements  Runnable{
    private List<Animal> animals = new ArrayList<>();
    private final WorldMap map;

    public Simulation(List<Vector2d> positions, WorldMap map, int genomeLength){
        this.map = map;

        for (Vector2d position : positions) {
            Genotype genes = Genotype.generateRandom(genomeLength);
            Animal animal = new Animal(position, genes);
            try {
                map.place(animal);
                animals.add(animal);
            } catch (IncorrectPositionException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run(){ // bedzie trzeba to zamienic na while True. Na razie dam tutaj po
        // prostu jakas stala.

        // usunięcie martwych zwierzaków
        animals = animals.stream()
            .filter(animal -> !animal.hasStarved())
            .collect(Collectors.toList());

        // ruch wszystkich zwierzaków
        for (int i = 0; i < 10; i++) { // ten for powinien obejmowac wszystkie metody,
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

        // zjadzanie roślin


        // rozmnażanie

        // wzrost nowych roślin



    }

    public List<Animal> getAnimals() {
        return animals;
    }
}
