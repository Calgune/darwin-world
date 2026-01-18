package agh.ics.oop;

import agh.ics.oop.model.*;
import agh.ics.oop.model.observers.SimulationChangeListener;
import agh.ics.oop.model.observers.StatisticsTracker;
import agh.ics.oop.model.simulation.Simulation;
import agh.ics.oop.model.util.MapSettings;
import agh.ics.oop.model.util.PriorityHandler;
import agh.ics.oop.model.util.RandomPositionGenerator;
import agh.ics.oop.model.util.WriteToCSV;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

abstract public class AbstractSimulation implements Simulation {
    protected final WorldMap map;
    protected final MapSettings settings;
    protected final ArrayList<SimulationChangeListener> simulationChangeListeners = new ArrayList<>();
    protected final StatisticsTracker statisticsTracker = new StatisticsTracker();
    private volatile boolean paused = false;
    protected int day;
    private volatile boolean running = true;
    private WriteToCSV csv;


    @Override
    public StatisticsTracker getStatistics(){
        return statisticsTracker;
    }

    @Override
    public void killSimulation() {
        this.running = false;
    }

    @Override
    public WriteToCSV getCSV() {
        return csv;
    }

    @Override
    public void startStop() {
        this.paused = !this.paused;
    }

    @Override
    public boolean isPaused() {
        return paused;
    }

    @Override
    abstract public MapVisualState getMapVisualState();

    @Override
    public int getDay(){
        return day;
    }

    @Override
    public void addObserver(SimulationChangeListener observer){
        simulationChangeListeners.add(observer);
    }

    @Override
    public void removeObserver(SimulationChangeListener observer){
        simulationChangeListeners.remove(observer);
    }

    @Override
    public void updateStatistics(){
        statisticsTracker.updateStats(map, day, 20);
        simulationChangeListeners.forEach(observer -> observer.simulationStatisticsChanged(statisticsTracker));
    }

    private void updateCharts() {
        simulationChangeListeners.forEach(observer -> observer.simulationChartsChanged(statisticsTracker));
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

    public AbstractSimulation(WorldMap map, MapSettings settings) {
        this.map = map;
        this.settings = settings;
        day = 0;

        spawnGrasses(settings.getInitialGrassCount());

        IntStream.range(0, settings.getInitialAnimalCount()).forEach(i -> {
            Genome genes = Genome.generateRandom(settings.getGenomeLength());

            Vector2d position = Vector2d.randomPositive(settings.getBoundary().upperRight().getX(),
                    settings.getBoundary().upperRight().getY());

            Animal animal = new Animal(position, genes, settings.getInitialAnimalEnergy());
            map.placeAnimal(animal);
        });
    }

    public void deleteDeadAnimals(){
        map.getAnimalsList().
                stream().
                filter(animal -> animal.hasStarved(day)).
                forEach(animal -> {
                    statisticsTracker.registerDeath(animal);
                    map.removeAnimal(animal);
                });
    }

    public void moveAnimals(){
        map.getAnimalsList().forEach(animal -> {
            map.move(animal);
        });
    }

    public void handleGrasses(){
        List<Grass> toRemove = map.getGrassesList().
                stream().
                filter(grass -> map.isOccupiedByAnimal(grass.getPosition())).
                toList();

        toRemove.forEach(grass -> {
            map.removeGrass(grass);
            List<Animal> animals = map.getAnimalsAt(grass.getPosition());
            Animal animal = PriorityHandler.whoEats(animals);
            if (animal != null){
                animal.eat(settings.getEnergyFromGrass());
            }
        });
    }

    public void breedAnimals(){
        map.getReproductionCells().forEach(animals -> {
            List<Animal> parents = PriorityHandler.whoBreeds(animals);
            if (parents.size() == 2) {
                Animal child = parents
                        .getFirst()
                        .breed(parents.get(1), this.settings);
                map.placeAnimal(child);
            }
        });
    }

    public void growGrasses(){
        spawnGrasses(settings.getNewDailyGrasses());
    }

    public void loseEnergy(){
        map.getAnimalsList().forEach(animal -> {
            animal.loseEnergy(settings.getDailyEnergyDecay());
            animal.age();
        });
    }

    public void advanceDay(){
        day++;
    }

    @Override
    public void runOneStep() {
        // 1 usunięcie martwych zwierzaków
        deleteDeadAnimals();

        // 2 ruch wszystkich zwierzaków
        moveAnimals();

        // 3 zjadzanie roślin (aktualizowanie energii, usunięcie rośliny)
        handleGrasses();

        // 4 rozmnażanie
        breedAnimals();

        // 5 wzrost nowych roślin
        growGrasses();

        // 6 utrata energii ze względu na przeminięcie dnia
        loseEnergy();

        // 7 przeminiecie dnia
        advanceDay();
        map.mapChanged("");
        updateStatistics();
        updateCharts();

        if (csv != null) {
            csv.writeData(new String[]{
                    String.valueOf(day),
                    String.valueOf(statisticsTracker.getAnimalsOnMap()),
                    String.valueOf(statisticsTracker.getGrassesOnMap()),
                    String.valueOf(statisticsTracker.getEmptyCells()),
                    String.valueOf(statisticsTracker.getMostPopularGenotype()),
                    String.valueOf(statisticsTracker.getAnimalsOnMapAverageEnergy()),
                    String.valueOf(statisticsTracker.getAnimalsOnMapAverageKidsCount()),
                    String.valueOf(statisticsTracker.getDeadAnimalsAverageLifespan())
            });
        }
    }

    @Override
    public void run(){
        map.mapChanged("");
        updateStatistics();
        updateCharts();
        csv = new WriteToCSV();

        while (running) {
            if (!paused) {
                runOneStep();
            }
            try {
                Thread.sleep(settings.getRefreshSpeed());
            } catch (InterruptedException e) {
                break;
            }
        }
        System.out.println("ZAMKNIETO SYMULACJE");
    }
}