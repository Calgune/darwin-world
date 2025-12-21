package integration;
import agh.ics.oop.Simulation;
import agh.ics.oop.model.Animal;
import agh.ics.oop.model.MoveDirection;
import agh.ics.oop.model.RectangularMap;
import agh.ics.oop.model.Vector2d;

import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SimulationTest {
    // sprawdza :
    // -czy zwierzęta wychodzą poza granice,
    // -czy stworzą się zwierzaki poza granicami mapy

    @Test
    void testSimulationWithSeveralAnimals() {
        List<MoveDirection> directions = List.of(
                MoveDirection.S,
                MoveDirection.N,
                MoveDirection.E,
                MoveDirection.N,
                MoveDirection.W
        );

        List<Vector2d> positions = List.of(
                new Vector2d(0, 0),
                new Vector2d(1, 1),
                new Vector2d(2, 3),
                new Vector2d(3, 3),
                new Vector2d(10, 3)
        );

        RectangularMap map = new RectangularMap(4, 4);
        Simulation simulation = new Simulation(positions, map, directions);
        simulation.run();

        List<Animal> animals = simulation.getAnimals();

        assertEquals(4, animals.size());

        assertEquals(new Vector2d(0, 0), animals.get(0).getPosition());
        assertEquals(new Vector2d(1, 2), animals.get(1).getPosition());
        assertEquals(new Vector2d(2, 3), animals.get(2).getPosition());
        assertEquals(new Vector2d(3, 3), animals.get(3).getPosition());
    }
}
