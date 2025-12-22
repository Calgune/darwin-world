//package integration;
//
//import agh.ics.oop.OptionsParser;
//import agh.ics.oop.model.*;
//import org.junit.jupiter.api.Test;
//import java.util.List;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//public class AnimalMovementTest {
//    @Test
//    void testAnimalMovement() {
//        String[] args = new String[]{"0", "4", "2", "6", "1", "5"};
//        List<MoveDirection> moveDirections = OptionsParser.parse(args);
//
//        List<MoveDirection> moveDirectionsProper = List.of(
//                MoveDirection.N,
//                MoveDirection.S,
//                MoveDirection.E,
//                MoveDirection.W,
//                MoveDirection.NE,
//                MoveDirection.SW
//        );
//
//        assertEquals(moveDirectionsProper, moveDirections);
//
//        MoveValidator map = new RectangularMap(4, 4);
//        Animal animal = new Animal(new Vector2d(2, 2));
//
//        assertEquals(MapDirection.NORTH, animal.getDirection());
//        assertEquals(new Vector2d(2, 2), animal.getPosition());
//
//        animal.move(moveDirections.getFirst(), map);
//        assertEquals(MapDirection.NORTH, animal.getDirection());
//        assertEquals(new Vector2d(2, 3), animal.getPosition());
//
//        animal.move(moveDirections.get(1), map);
//        assertEquals(MapDirection.SOUTH, animal.getDirection());
//        assertEquals(new Vector2d(2, 2), animal.getPosition());
//
//        animal.move(moveDirections.get(2), map);
//        assertEquals(MapDirection.EAST, animal.getDirection());
//        assertEquals(new Vector2d(3, 2), animal.getPosition());
//
//        animal.move(moveDirections.get(3), map);
//        assertEquals(MapDirection.WEST, animal.getDirection());
//        assertEquals(new Vector2d(2, 2), animal.getPosition());
//
//        animal.move(moveDirections.get(4), map);
//        assertEquals(MapDirection.NORTH_EAST, animal.getDirection());
//        assertEquals(new Vector2d(3, 3), animal.getPosition());
//
//        animal.move(moveDirections.get(5), map);
//        assertEquals(MapDirection.SOUTH_WEST, animal.getDirection());
//        assertEquals(new Vector2d(2, 2), animal.getPosition());
//
//        animal.move(MoveDirection.N, map);
//        assertEquals(new Vector2d(2, 3), animal.getPosition());
//    }
//}