package integration;

import agh.ics.oop.NormalSimulation;
import agh.ics.oop.model.*;
import agh.ics.oop.model.maps.CylindricalMap;
import agh.ics.oop.model.simulation.Simulation;
import agh.ics.oop.model.util.MapSettings;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SimulationIntegrationTest {
    @Test
    void testAnimalStarvationAndRemoval() {
        MapSettings settings = new MapSettings.Builder()
                .boundary(new Boundary(new Vector2d(0, 0), new Vector2d(10, 10)))
                .initialAnimalEnergy(5)
                .dailyEnergyDecay(10)
                .initialAnimalCount(0)
                .genomeLength(1)
                .build();

        WorldMap map = new CylindricalMap(settings);
        Vector2d position = new Vector2d(2, 2);
        Animal animal = new Animal(position, new Genome(List.of(0)), 5);
        map.placeAnimal(animal);

        Simulation simulation = new NormalSimulation(map, settings);

        simulation.runOneStep();
        //zwierzeta usuwane sa na poczatku kolejnego dnia
        simulation.runOneStep();

        assertTrue(animal.getEnergy() <= 0);
        assertTrue(map.getAnimalsList().isEmpty());
    }

    @Test
    void testReproductionProcess() {
        MapSettings settings = new MapSettings.Builder()
                .boundary(new Boundary(new Vector2d(0, 0), new Vector2d(10, 10)))
                .initialAnimalEnergy(100)
                .reproductionEnergyThreshold(50)
                .reproductionEnergyCost(30)
                .initialAnimalCount(0)
                .dailyEnergyDecay(0)
                .genomeLength(1)
                .build();

        WorldMap map = new CylindricalMap(settings);
        Vector2d position = new Vector2d(1, 1);

        Animal parent1 = new Animal(position, new Genome(List.of(0)), 100);
        Animal parent2 = new Animal(position, new Genome(List.of(0)), 100);

        parent1.setDirection(MapDirection.NORTH);
        parent2.setDirection(MapDirection.NORTH);
        map.placeAnimal(parent1);
        map.placeAnimal(parent2);

        Simulation simulation = new NormalSimulation(map, settings);

        simulation.runOneStep();

        assertEquals(3, map.getAnimalsList().size());
        assertEquals(70, parent1.getEnergy());
        assertEquals(70, parent2.getEnergy());

        Animal child = null;
        for (Animal a : map.getAnimalsList()) {
            if (a != parent1 && a != parent2) {
                child = a;
                break;
            }
        }
        assertNotNull(child);
        assertEquals(60, child.getEnergy());
    }

    @Test
    void testEating() {
        MapSettings settings = new MapSettings.Builder()
                .boundary(new Boundary(new Vector2d(0, 0), new Vector2d(10, 10)))
                .initialAnimalEnergy(20)
                .energyFromGrass(15)
                .dailyEnergyDecay(5)
                .initialAnimalCount(0)
                .build();

        WorldMap map = new CylindricalMap(settings);
        Vector2d position = new Vector2d(5, 5);
        Animal animal = new Animal(position, new Genome(List.of(0)), 20);
        animal.setDirection(MapDirection.NORTH);
        map.placeAnimal(animal);

        Vector2d grassPos = new Vector2d(5, 6);
        map.placeGrass(new Grass(grassPos));

        Simulation simulation = new NormalSimulation(map, settings);
        simulation.runOneStep();

        assertEquals(30, animal.getEnergy());
        assertFalse(map.isOccupiedByGrass(grassPos));
    }

    @Test
    void testMovementSequence() {
        MapSettings settings = new MapSettings.Builder()
                .boundary(new Boundary(new Vector2d(0, 0), new Vector2d(10, 10)))
                .initialAnimalCount(0)
                .dailyEnergyDecay(0)
                .build();

        WorldMap map = new CylindricalMap(settings);
        Animal animal = new Animal(new Vector2d(5, 5), new Genome(List.of(2, 0)), 100);
        animal.setDirection(MapDirection.NORTH);
        map.placeAnimal(animal);

        Simulation simulation = new NormalSimulation(map, settings);

        simulation.runOneStep();
        assertEquals(new Vector2d(6, 5), animal.getPosition());

        simulation.runOneStep();
        assertEquals(new Vector2d(7, 5), animal.getPosition());
    }
}