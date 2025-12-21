//package agh.ics.oop.model;
//
//import agh.ics.oop.model.util.MapVisualizer;
//import org.junit.jupiter.api.Test;
//
//import java.util.Collection;
//import java.util.HashSet;
//import java.util.Set;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class RectangularMapTest {
//
//    @Test
//    void place() {
//        RectangularMap map = new RectangularMap(4,4);
//
//        assertDoesNotThrow(() -> map.place(new Animal(new Vector2d(2, 2))));
//        assertDoesNotThrow(() -> map.place(new Animal(new Vector2d(2, 3))));
//        assertThrows(IncorrectPositionException.class, () -> map.place(new Animal(new Vector2d(2, 2))));
//        assertThrows(IncorrectPositionException.class, () -> map.place(new Animal(new Vector2d(4, 4))));
//        assertThrows(IncorrectPositionException.class, () -> map.place(new Animal(new Vector2d(5, 6))));
//        assertThrows(IncorrectPositionException.class, () -> map.place(new Animal(new Vector2d(3, 10))));
//        assertThrows(IncorrectPositionException.class, () -> map.place(new Animal(new Vector2d(10, 3))));
//
//    }
//
//    @Test
//    void move() {
//        RectangularMap map = new RectangularMap(4,4);
//        Animal animal1 = new Animal(new Vector2d(2, 2));
//        assertDoesNotThrow(() -> map.place(animal1));
//
//        map.move(animal1, MoveDirection.FORWARD);
//        map.move(animal1, MoveDirection.LEFT);
//        map.move(animal1, MoveDirection.FORWARD);
//
//        assertEquals(new Vector2d(1,3), animal1.getPosition());
//        assertEquals(animal1, map.objectAt(new Vector2d(1, 3)));
//    }
//
//    @Test
//    void isOccupied() {
//        RectangularMap map = new RectangularMap(4,4);
//
//        assertDoesNotThrow(() -> map.place(new Animal(new Vector2d(2, 2))));
//
//        assertTrue(map.isOccupied(new Vector2d(2, 2)));
//        assertFalse(map.isOccupied(new Vector2d(10, 2)));
//        assertFalse(map.isOccupied(new Vector2d(2, 10)));
//        assertFalse(map.isOccupied(new Vector2d(10, 10)));
//    }
//
//    @Test
//    void objectAt() {
//        RectangularMap map = new RectangularMap(4,4);
//        Animal animal1 = new Animal(new Vector2d(2, 2));
//        Animal animal2 = new Animal(new Vector2d(2, 2));
//        Animal animal3 = new Animal(new Vector2d(2, 3));
//        Animal animal4 = new Animal(new Vector2d(5, 5));
//
//        assertDoesNotThrow(() -> map.place(animal1));
//        assertThrows(IncorrectPositionException.class, () -> map.place(animal2));
//        assertDoesNotThrow(() -> map.place(animal3));
//        assertThrows(IncorrectPositionException.class, () -> map.place(animal4));
//
//        assertEquals(animal1, map.objectAt(new Vector2d(2, 2)));
//        assertEquals(animal3, map.objectAt(new Vector2d(2, 3)));
//        assertNull(map.objectAt(new Vector2d(3, 3)));
//        assertNull(map.objectAt(new Vector2d(5, 5)));
//    }
//
//    @Test
//    void canMoveTo() {
//        RectangularMap map = new RectangularMap(4,4);
//
//        assertTrue(map.canMoveTo(new Vector2d(1, 2)));
//        assertTrue(map.canMoveTo(new Vector2d(0, 0)));
//        assertTrue(map.canMoveTo(new Vector2d(1, 0)));
//        assertTrue(map.canMoveTo(new Vector2d(0, 1)));
//        assertTrue(map.canMoveTo(new Vector2d(3, 3)));
//        assertFalse(map.canMoveTo(new Vector2d(3, 4)));
//        assertFalse(map.canMoveTo(new Vector2d(4, 3)));
//        assertFalse(map.canMoveTo(new Vector2d(5, 5)));
//        assertFalse(map.canMoveTo(new Vector2d(10, 5)));
//    }
//
//    @Test
//    void testToString() {
//        RectangularMap map = new RectangularMap(4,4);
//        try {
//            map.place(new Animal(new Vector2d(0,0)));
//            map.place(new Animal(new Vector2d(2,3)));
//            map.place(new Animal(new Vector2d(3,3)));
//        }
//        catch (IncorrectPositionException e){
//            fail();
//        }
//
//        String excepted = " y\\x  0 1 2 3\r\n  4: ---------\r\n  3: | | |N|N|\r\n  2: | | | | |\r\n  1: | | | | |\r\n  0: |N| | | |\r\n -1: ---------\r\n";
//        assertEquals(excepted, map.toString());
//    }
//
//    @Test
//    void testGetElements(){
//        RectangularMap map = new RectangularMap(4,4);
//        Set<WorldElement> animals = new HashSet<>();
//
//        Animal animal1 = new Animal(new Vector2d(0,0));
//        Animal animal2 = new Animal(new Vector2d(2, 3));
//        Animal animal3 = new Animal(new Vector2d(3, 3));
//
//
//        animals.add(animal1);
//        animals.add(animal2);
//        animals.add(animal3);
//
//        try {
//            map.place(animal1);
//            map.place(animal2);
//            map.place(animal3);
//        }
//        catch (IncorrectPositionException e){
//            fail();
//        }
//
//        Collection<WorldElement> collection = map.getElements();
//        assertTrue(animals.containsAll(collection));
//    }
//}
