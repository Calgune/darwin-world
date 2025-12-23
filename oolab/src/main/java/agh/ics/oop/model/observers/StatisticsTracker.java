package agh.ics.oop.model.observers;

import agh.ics.oop.model.Animal;
import agh.ics.oop.model.Genome;
import agh.ics.oop.model.WorldMap;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StatisticsTracker implements SimulationObserver {
    private WorldMap map;

    private int animalsOnMap;
    private int grassesOnMap;
    private int emptyCells;
    private int mostPopularGenotype;
    private double deadAnimalsAverageLifespan;
    private double animalsOnMapAverageEnergy;
    private double animalsOnMapAverageKidsCount;

    private int deadAnimalsCount = 0;
    private int deadAnimalsAgeSum = 0;

    private int getMostPopularGenotype(){
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

    private double getAnimalsOnMapAverageEnergy(){
        return map.getAnimalsList().stream().mapToInt(Animal::getEnergy).average().orElse(0);
    }

    private double getAnimalsOnMapAverageKidsCount(){
        return map.getAnimalsList().stream().mapToInt(Animal::getKidsCount).average().orElse(0);
    }

    private double getDeadAnimalsAverageLifespan(){
        return deadAnimalsCount == 0 ? 0 :
                (double) deadAnimalsAgeSum / deadAnimalsCount;
    }

    @Override
    public void registerDeath(Animal animal){
        deadAnimalsAgeSum += animal.getAge();
        deadAnimalsCount++;
    }

    // skala projektu jest zbyt mała, aby aktualizować statystyki dopiero po
    // konkretnym wydarzeniu lepiej jeden raz zawsze wszystkie
    // czytelność lepsza niż mikrooptymalizacja
    @Override
    public void updateStats(WorldMap map) {
        this.map = map;

        animalsOnMap = map.getAnimalsCount();
        grassesOnMap = map.getGrassesCount();
        emptyCells = map.getEmptySpacesCount();

        mostPopularGenotype = getMostPopularGenotype();

        deadAnimalsAverageLifespan = getDeadAnimalsAverageLifespan();
        animalsOnMapAverageEnergy = getAnimalsOnMapAverageEnergy();
        animalsOnMapAverageKidsCount = getAnimalsOnMapAverageKidsCount();

        IO.println("%d, %d, %d, %d, %f, %f, %f".formatted(animalsOnMap, grassesOnMap, emptyCells, mostPopularGenotype,
                deadAnimalsAverageLifespan, animalsOnMapAverageEnergy, animalsOnMapAverageKidsCount));
    }



}
