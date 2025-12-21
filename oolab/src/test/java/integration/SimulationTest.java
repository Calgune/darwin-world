package integration;

import agh.ics.oop.OptionsParser;
import agh.ics.oop.Simulation;
import agh.ics.oop.model.Animal;
import agh.ics.oop.model.MoveDirection;
import agh.ics.oop.model.RectangularMap;
import agh.ics.oop.model.Vector2d;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SimulationTest {

    // sprawdza :
    // -czy zwierzęta wychodzą poza granice,
    // -nienakładaja się na siebie zwierzaki na starcie
    // -nienakładaja się na siebie zwierzaki gdy się poruszają
    // -czy stworzą się zwierzaki poza granicami mapy

    @Test
    void testSimulationWithSeveralAnimals(){
        List<MoveDirection> directions = List.of(MoveDirection.BACKWARDS, MoveDirection.FORWARD, MoveDirection.FORWARD,
                MoveDirection.BACKWARDS, MoveDirection.RIGHT, MoveDirection.FORWARD,
                MoveDirection.BACKWARDS, MoveDirection.FORWARD, MoveDirection.FORWARD,
                MoveDirection.BACKWARDS, MoveDirection.LEFT, MoveDirection.FORWARD,
                MoveDirection.LEFT, MoveDirection.BACKWARDS, MoveDirection.RIGHT,
                MoveDirection.FORWARD, MoveDirection.BACKWARDS, MoveDirection.FORWARD);

        List<Vector2d> positions = List.of(new Vector2d(0,0),
                new Vector2d(0,0),
                new Vector2d(2,3),
                new Vector2d(3,3),
                new Vector2d(10,3));

        RectangularMap map = new RectangularMap(4, 4);
        Simulation simulation = new Simulation(positions, map, directions);
        simulation.run();


        List<Animal> animals = simulation.getAnimals();

        assertEquals(3, animals.size());
        assertEquals(new Vector2d(0, 0), animals.getFirst().getPosition());
        assertEquals(new Vector2d(2, 1), animals.get(1).getPosition());
        assertEquals( new Vector2d(3, 3), animals.get(2).getPosition());
    }
}
