package agh.ics.oop;

import agh.ics.oop.model.*;
import agh.ics.oop.model.maps.NormalVisualState;
import agh.ics.oop.model.maps.SeasonalVisualState;
import agh.ics.oop.model.simulation.SeasonalInfo;
import agh.ics.oop.model.util.MapSettings;
import agh.ics.oop.model.util.PriorityHandler;
import agh.ics.oop.model.util.Season;

import java.util.List;
import java.util.Map;

public class SeasonalSimulation extends AbstractSimulation implements SeasonalInfo {
    private final SeasonManager seasonManager;

    @Override
    public void updateStatistics(){
        statisticsTracker.updateStats(map, day, seasonManager.getTemperature(day));
        simulationChangeListeners.forEach(observer -> observer.simulationStatisticsChanged(statisticsTracker));
    }

    @Override
    public MapVisualState getMapVisualState() {
        if(seasonManager.getSeason(day) == Season.Winter){
            double y = seasonManager.getTemperatureToColdnessFactor(day);
            return new SeasonalVisualState(y);
        }
        return new NormalVisualState();
    }

    public SeasonalSimulation(WorldMap map, MapSettings settings){
        super(map, settings);
        seasonManager = new SeasonManager(settings);
    }

    @Override
    public void handleGrasses(){
        List<Grass> toRemove = map.getGrassesList().
                stream().
                filter(grass -> map.isOccupiedByAnimal(grass.getPosition())).
                toList();

        toRemove.forEach(grass -> {
            map.removeGrass(grass);
            List<Animal> animals = map.getAnimalsAt(grass.getPosition());
            Animal animal = PriorityHandler.whoEats(animals);
            animal.eat((int) (settings.getEnergyFromGrass()*seasonManager.getEnergyFromGrassModifier(day)));
        });
    }

    @Override
    public void growGrasses(){
        spawnGrasses((int)(settings.getNewDailyGrasses()*seasonManager.getGrassSpawnModifier(day)));
    }

    @Override
    public void loseEnergy(){
        Map<Vector2d, Integer> neighborsDensity = map.getNeighborsDensityMap();

        map.getAnimalsList().forEach(animal -> {
            // zakładam, że im więcej zwierzaków tym więcej ciepła więc mniej tracą energi
            // odpowiednia liczba zwierzaków jest wstanie zrekompensować straty energi spowodowane zimą
            // każdy zwierzak dodatkowy, ponad bazowy warunek odpowiednio zwiększa ciepło o stałą wartość
            int animalsCountingToDrainReduction = Math.max(0, neighborsDensity.getOrDefault(animal.getPosition(), 0)
                    -settings.getMinimalAnimalCountInRangeToTransferHeat() + 1);

            double potentialEnergyDrain = settings.getDailyEnergyDecay()*seasonManager.getEnergyDecayModifier(day)
                    -settings.getEnergyDrainReduction()*animalsCountingToDrainReduction;

            double minimalEnergyDrain = settings.getDailyEnergyDecay();

            animal.loseEnergy((int) (Math.max(potentialEnergyDrain, minimalEnergyDrain)));
            animal.age();
        });
    }

    @Override
    public double getTemperature() {
        return seasonManager.getTemperature(day);
    }

    @Override
    public Season getSeason() {
        return seasonManager.getSeason(day);
    }



}
