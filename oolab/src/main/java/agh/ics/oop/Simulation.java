package agh.ics.oop;

import agh.ics.oop.model.*;
import agh.ics.oop.model.observers.SimulationObserver;
import agh.ics.oop.model.util.MapSettings;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class Simulation implements Runnable {
    private final WorldMap map;
    private final MapSettings settings;
    private final ArrayList<SimulationObserver> simulationObservers = new ArrayList<>();
    private volatile boolean paused = false;

    public void startStop() {
        paused = !paused;
    }

    public void addObserver(SimulationObserver observer){
        simulationObservers.add(observer);
    }

    public void removeObserver(SimulationObserver observer){
        simulationObservers.remove(observer);
    }

    public void simulationChanged(){
        simulationObservers.forEach(simulationObserver -> {simulationObserver.updateStats(map);});
    }

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
            Genome genes = Genome.generateRandom(settings.getGenomeLength());

            Vector2d position = Vector2d.randomPositive(settings.getBoundary().upperRight().getX(),
                    settings.getBoundary().upperRight().getY());

            Animal animal = new Animal(position, genes, settings.getInitialAnimalEnergy());
            map.placeAnimal(animal);
        });
    }

    @Override
    public void run(){
        while (true) {
            if (!paused) {
                // 1 usunięcie martwych zwierzaków
                map.getAnimalsList().
                        stream().
                        filter(Animal::hasStarved).
                        forEach(animal -> {
                            simulationObservers.forEach(obs -> obs.registerDeath(animal));
                            map.removeAnimal(animal);
                        });

                // 2 ruch wszystkich zwierzaków
                map.getAnimalsList().forEach(animal -> {
                    map.move(animal);
                    try {
                        Thread.sleep(settings.getRefreshSpeed());
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                });

                // 3 zjadzanie roślin (aktualizowanie energi, usunięcie rośliny
                List<Grass> toRemove = map.getGrassesList().
                        stream().
                        filter(grass -> map.isOccupiedByAnimal(grass.getPosition())).
                        toList();

                toRemove.forEach(grass -> {
                    map.removeGrass(grass);
                    List<Animal> animals = map.getAnimalsAt(grass.getPosition());
                    Animal animal = PriorityHandler.whoEats(animals);
                    animal.eat(settings.getEnergyFromGrass());
                });

                // 4 rozmnażanie
                map.getReproductionCells().forEach(animals -> {
                    List<Animal> parents = PriorityHandler.whoBreeds(animals);
                    if (parents.size() == 2) {
                        Animal child = parents
                                .getFirst()
                                .breed(parents.get(1), settings.getReproductionEnergyCost());
                        map.placeAnimal(child);
                    }
                });

                // 5 wzrost nowych roślin
                spawnGrasses(settings.getNewDailyGrasses());

                // 6 utrata energi zewzględu na przeminięcie dnia
                map.getAnimalsList().forEach(animal -> {
                    animal.loseEnergy(settings.getDailyEnergyDecay());
                    animal.age();
                });

                // 7 przeminiecie dnia
                map.advanceDay();
                simulationChanged();
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}




