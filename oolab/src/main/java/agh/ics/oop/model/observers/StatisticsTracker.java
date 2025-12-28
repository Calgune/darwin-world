package agh.ics.oop.model.observers;

import agh.ics.oop.model.Animal;
import agh.ics.oop.model.Vector2d;
import agh.ics.oop.model.WorldMap;
import agh.ics.oop.model.util.ChartData;

import java.util.*;

public class StatisticsTracker {
    private int animalsOnMap;
    private int grassesOnMap;
    private int emptyCells;
    private int mostPopularGenotype;
    private double deadAnimalsAverageLifespan;
    private double animalsOnMapAverageEnergy;
    private double animalsOnMapAverageKidsCount;

    private int deadAnimalsCount = 0;
    private int deadAnimalsAgeSum = 0;

    private ChartData chartData;

    private int calculateMostPopularGenotype(WorldMap map){
        List<Integer> genotypes = map.getAnimalsList().stream()
                .map(animal -> animal.getGenome().getGenes())
                .flatMap(List::stream)
                .toList();

        Map<Integer, Integer> countGenotypes = new HashMap<>();
        genotypes.forEach(genotype -> {
            countGenotypes.put(genotype, countGenotypes.getOrDefault(genotype, 0) + 1);
        });

        int max = -1;
        int maxGenotype = -1;
        for (Map.Entry<Integer, Integer> entry : countGenotypes.entrySet()) {
            if (max < entry.getValue()){
                max = entry.getValue();
                maxGenotype = entry.getKey();
            }
        }
        return maxGenotype;
    }

    private double calculateAnimalsOnMapAverageEnergy(WorldMap map){
        return map.getAnimalsList().stream().mapToInt(Animal::getEnergy).average().orElse(0);
    }

    private double calculateAnimalsOnMapAverageKidsCount(WorldMap map){
        return map.getAnimalsList().stream().mapToInt(Animal::getKidsCount).average().orElse(0);
    }

    public double calculateDeadAnimalsAverageLifespan(WorldMap map){
        return deadAnimalsCount == 0 ? 0 :
                (double) deadAnimalsAgeSum / deadAnimalsCount;
    }

    public void registerDeath(Animal animal){
        deadAnimalsAgeSum += animal.getAge();
        deadAnimalsCount++;
    }

    public ChartData getChartData(){
        return chartData;
    }

    // skala projektu jest zbyt mała, aby aktualizować statystyki dopiero po
    // konkretnym wydarzeniu lepiej jeden raz zawsze wszystkie
    // czytelność lepsza niż mikrooptymalizacja
    public void updateStats(WorldMap map, int day) {
        animalsOnMap = map.getAnimalsCount();
        grassesOnMap = map.getGrassesCount();
        emptyCells = map.getEmptySpacesCount();

        mostPopularGenotype = calculateMostPopularGenotype(map);

        deadAnimalsAverageLifespan = calculateDeadAnimalsAverageLifespan(map);
        animalsOnMapAverageEnergy = calculateAnimalsOnMapAverageEnergy(map);
        animalsOnMapAverageKidsCount = calculateAnimalsOnMapAverageKidsCount(map);

        chartData = new ChartData(day, animalsOnMap,
                grassesOnMap, animalsOnMapAverageEnergy, deadAnimalsAverageLifespan, animalsOnMapAverageKidsCount);
    }

    public int getDeadAnimalsAgeSum() {
        return deadAnimalsAgeSum;
    }

    public int getDeadAnimalsCount() {
        return deadAnimalsCount;
    }

    public int getEmptyCells() {
        return emptyCells;
    }

    public int getGrassesOnMap() {
        return grassesOnMap;
    }

    public int getAnimalsOnMap() {
        return animalsOnMap;
    }

    public int getMostPopularGenotype() {
        return mostPopularGenotype;
    }

    public double getDeadAnimalsAverageLifespan() {
        return deadAnimalsAverageLifespan;
    }

    public double getAnimalsOnMapAverageEnergy() {
        return animalsOnMapAverageEnergy;
    }

    public double getAnimalsOnMapAverageKidsCount() {
        return animalsOnMapAverageKidsCount;
    }
}
