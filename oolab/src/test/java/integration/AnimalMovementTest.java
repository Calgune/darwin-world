package integration;

import agh.ics.oop.OptionsParser;
import agh.ics.oop.model.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AnimalMovementTest {
    @Test
    void testAnimalMovement(){

        // sprawdzamy dane wejściowe są dobrze interpretowane przez optionparser
        String[] args = new String[]{"f", "b", "r", "l", "f", "f", "r", "r", "f", "x", "x", "1", "f", "f", "f", "l", "f", "f", "f", "b", "b", "b", "b", "b"};
        List<MoveDirection> moveDirections = OptionsParser.parse(args);
        List<MoveDirection> moveDirectionsProper = List.of(MoveDirection.FORWARD,
                MoveDirection.BACKWARDS,
                MoveDirection.RIGHT,
                MoveDirection.LEFT,
                MoveDirection.FORWARD,
                MoveDirection.FORWARD,
                MoveDirection.RIGHT,
                MoveDirection.RIGHT,
                MoveDirection.FORWARD,
                MoveDirection.FORWARD,
                MoveDirection.FORWARD,
                MoveDirection.FORWARD,
                MoveDirection.LEFT,
                MoveDirection.FORWARD,
                MoveDirection.FORWARD,
                MoveDirection.FORWARD,
                MoveDirection.BACKWARDS,
                MoveDirection.BACKWARDS,
                MoveDirection.BACKWARDS,
                MoveDirection.BACKWARDS,
                MoveDirection.BACKWARDS);

        assertEquals(moveDirections, moveDirectionsProper);
        MoveValidator map = new RectangularMap(5, 5);

        //sprawdza orienacje zwierzaka, ogolne poruszanie sie po mapie i wychodzenie poza granice
        Animal animal = new Animal(new Vector2d(2, 3));

        assertEquals(MapDirection.NORTH, animal.getDirection());
        assertEquals(new Vector2d(2,3), animal.getPosition());

        animal.move(moveDirections.getFirst(), map);
        assertEquals(MapDirection.NORTH, animal.getDirection());
        assertEquals(new Vector2d(2, 4), animal.getPosition());

        animal.move(moveDirections.get(1), map);
        assertEquals(MapDirection.NORTH, animal.getDirection());
        assertEquals(new Vector2d(2, 3), animal.getPosition());

        animal.move(moveDirections.get(2), map);
        assertEquals(MapDirection.EAST, animal.getDirection());
        assertEquals(new Vector2d(2, 3), animal.getPosition());

        animal.move(moveDirections.get(3), map);
        assertEquals(MapDirection.NORTH, animal.getDirection());
        assertEquals(new Vector2d(2, 3), animal.getPosition());

        animal.move(moveDirections.get(4), map);
        assertEquals(MapDirection.NORTH, animal.getDirection());
        assertEquals(new Vector2d(2, 4), animal.getPosition());

        animal.move(moveDirections.get(5), map);
        assertEquals(MapDirection.NORTH, animal.getDirection());
        assertEquals(new Vector2d(2, 4), animal.getPosition());

        animal.move(moveDirections.get(6), map);
        assertEquals(MapDirection.EAST, animal.getDirection());
        assertEquals(new Vector2d(2, 4), animal.getPosition());

        animal.move(moveDirections.get(7), map);
        assertEquals(MapDirection.SOUTH, animal.getDirection());
        assertEquals(new Vector2d(2, 4), animal.getPosition());

        animal.move(moveDirections.get(8), map);
        assertEquals(MapDirection.SOUTH, animal.getDirection());
        assertEquals(new Vector2d(2, 3), animal.getPosition());

        animal.move(moveDirections.get(9), map);
        assertEquals(MapDirection.SOUTH, animal.getDirection());
        assertEquals(new Vector2d(2, 2), animal.getPosition());

        animal.move(moveDirections.get(10), map);
        assertEquals(MapDirection.SOUTH, animal.getDirection());
        assertEquals(new Vector2d(2, 1), animal.getPosition());

        animal.move(moveDirections.get(11), map);
        assertEquals(MapDirection.SOUTH, animal.getDirection());
        assertEquals(new Vector2d(2, 0), animal.getPosition());

        animal.move(moveDirections.get(12), map);
        assertEquals(MapDirection.EAST, animal.getDirection());
        assertEquals(new Vector2d(2, 0), animal.getPosition());

        animal.move(moveDirections.get(13), map);
        assertEquals(MapDirection.EAST, animal.getDirection());
        assertEquals(new Vector2d(3, 0), animal.getPosition());

        animal.move(moveDirections.get(14), map);
        assertEquals(MapDirection.EAST, animal.getDirection());
        assertEquals(new Vector2d(4, 0), animal.getPosition());

        animal.move(moveDirections.get(15), map);
        assertEquals(MapDirection.EAST, animal.getDirection());
        assertEquals(new Vector2d(4, 0), animal.getPosition());

        animal.move(moveDirections.get(16), map);
        assertEquals(MapDirection.EAST, animal.getDirection());
        assertEquals(new Vector2d(3, 0), animal.getPosition());

        animal.move(moveDirections.get(17), map);
        assertEquals(MapDirection.EAST, animal.getDirection());
        assertEquals(new Vector2d(2, 0), animal.getPosition());

        animal.move(moveDirections.get(18), map);
        assertEquals(MapDirection.EAST, animal.getDirection());
        assertEquals(new Vector2d(1, 0), animal.getPosition());

        animal.move(moveDirections.get(19), map);
        assertEquals(MapDirection.EAST, animal.getDirection());
        assertEquals(new Vector2d(0, 0), animal.getPosition());

        animal.move(moveDirections.get(20), map);
        assertEquals(MapDirection.EAST, animal.getDirection());
        assertEquals(new Vector2d(0, 0), animal.getPosition());

    }

}
