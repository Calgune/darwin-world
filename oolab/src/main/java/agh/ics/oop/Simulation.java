package agh.ics.oop;

import agh.ics.oop.model.*;
import agh.ics.oop.model.util.MapSettings;
import com.sun.scenario.Settings;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Simulation implements  Runnable {
    private final WorldMap map;
    private final MapSettings settings;

    public void spawnGrasses(int grassToSpawn){
        int grassInJungle = (int) (Math.ceil(grassToSpawn * 0.8f));
        int grassOnSteppes = (int) (Math.floor(grassToSpawn * 0.2f));

        RandomPositionGenerator jungleGen = new RandomPositionGenerator(map.getJungleBoundary(), grassInJungle);
        jungleGen.forEach(pos -> map.placeGrass(new Grass(pos)));

        Random random = new Random();
        int grassCountOnNorth = (int) Math.ceil(grassOnSteppes / 2.0f);
        int grassCountOnSouth = (int) Math.floor(grassOnSteppes / 2.0f);

        if (random.nextInt(0, 2) == 0) {
            int temp = grassCountOnNorth;
            grassCountOnNorth = grassCountOnSouth;
            grassCountOnSouth = temp;
        }

        RandomPositionGenerator northGen = new RandomPositionGenerator(map.getNorthSteppes(), grassCountOnNorth);
        RandomPositionGenerator southGen = new RandomPositionGenerator(map.getSouthSteppes(), grassCountOnSouth);

        northGen.forEach(pos -> map.placeGrass(new Grass(pos)));
        southGen.forEach(pos -> map.placeGrass(new Grass(pos)));
    }

    public Simulation(WorldMap map, MapSettings settings) { //POPRAWIC
        this.map = map;
        this.settings = settings;

        spawnGrasses(settings.getInitialGrassCount());

        //spawnowanie zwierząt na początku
        Random random = new Random();
        IntStream.range(0, settings.getInitialAnimalCount()).forEach(i -> {
            Genotype genes = Genotype.generateRandom(settings.getGenomeLength());

            Vector2d position = Vector2d.randomPositive(settings.getBoundary().upperRight().getX(),
                    settings.getBoundary().upperRight().getY());

            Animal animal = new Animal(position, genes, settings.getInitialAnimalEnergy());
            map.placeAnimal(animal);
        });
    }

    @Override
    public void run(){
        for (int i = 0; i < 1000; i++) { // ten for powinien obejmowac wszystkie metody,
            // 1 usunięcie martwych zwierzaków
            // zwierzeta trzeba jeszcze z mapy usunąć
            map.getAnimalsList().
                    stream().
                    filter(Animal::hasStarved).
                    forEach(map::removeAnimal);

            // 2 ruch wszystkich zwierzaków
            // bedzie trzeba to zamienic na while True. Na razie dam tutaj stałą
            // ale dopoki ich nie ma to rusza tylko zwierzakami.

            map.getAnimalsList().forEach(animal -> {
                map.move(animal);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });

           // 3 zjadzanie roślin
           // aktualizowanie energi, usunięcie rośliny
            List<Grass> toRemove = map.getGrassesList().
                    stream().
                    filter(grass -> map.isOccupiedByAnimal(grass.getPosition())).
                    toList();

            // usunięcie rośliny
            toRemove.forEach(grass -> {
                map.removeGrass(grass);
                List<Animal> animals = map.getAnimalsAt(grass.getPosition());
                Animal animal = PriorityHandler.whoEats(animals);
                animal.eat(settings.getEnergyFromGrass());
            });

           // 4 rozmnażanie

            map.getAnimalHashMap().entrySet().
                    stream().
                    filter(entry -> entry.getValue().size() >= 2).
                    // szukamy tylko tych pol na ktorych sa co najmniej 2 zwierzaki
                    forEach(entry -> {
                        Vector2d position = entry.getKey();
                        List<Animal> animals = entry.getValue();

                        List<Animal> parents = PriorityHandler.whoBreeds(animals);
                        Animal a = parents.get(0);
                        Animal b = parents.get(1);

                        if (a.getEnergy() >= settings.getReproductionEnergyThreshold() &&
                                b.getEnergy() >= settings.getReproductionEnergyThreshold()) {

                            Animal child = a.breed(b, settings.getReproductionEnergyCost());
                            map.placeAnimal(child);
                        }
                    });


           // aktualizowanie energi

           // 5 wzrost nowych roślin
            spawnGrasses(settings.getNewDailyGrasses());

           // 6 utrata energi zewzględu na przeminięcie dnia
            map.getAnimalsList().forEach(animal -> animal.loseEnergy(settings.getDailyEnergyDecay()));
        }
    }
}



