package agh.ics.oop.model;

import com.sun.net.httpserver.Authenticator;
import org.junit.jupiter.api.Test;

import javax.lang.model.element.Element;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class GrassFieldTest {

    @Test
    void place() {
        GrassField map = new GrassField(0);
        map.placeGrass(new Grass(new Vector2d(2, 2)));

        assertDoesNotThrow(() -> map.place(new Animal(new Vector2d(2, 2))));
        assertDoesNotThrow(() -> map.place(new Animal(new Vector2d(7, 3))));
        assertDoesNotThrow(() -> map.place(new Animal(new Vector2d(1, 2))));

        // .class bo assertThrows porównuje typy wyjątków a nie obiekty.
        assertThrows(IncorrectPositionException.class, () -> map.place(new Animal(new Vector2d(2, 2))));

    }

    // ruszanie nie ma ograniczeń
    // wystarczy sprawdzić czy zwierzęta się nie nachodzą na siebie

    @Test
    void move() {
        GrassField grasses = new GrassField(0);

        Grass grass1 = new Grass(new Vector2d(2, 3));
        Grass grass2 = new Grass(new Vector2d(10, 10));

        Animal animal1 = new Animal(new Vector2d(2, 2));
        Animal animal2 = new Animal(new Vector2d(2, 1));
        Animal animal3 = new Animal(new Vector2d(5, 5));

        grasses.placeGrass(grass1);
        grasses.placeGrass(grass2);

        try {
            grasses.place(animal1);
            grasses.place(animal2);
            grasses.place(animal3);
        }
        catch (IncorrectPositionException e){
            fail();
        }

        grasses.move(animal1, MoveDirection.BACKWARDS);
        grasses.move(animal1, MoveDirection.FORWARD);
        grasses.move(animal3, MoveDirection.RIGHT);
        grasses.move(animal3, MoveDirection.FORWARD);

        assertEquals(new Vector2d(2, 3), animal1.getPosition());
        assertEquals(new Vector2d(6, 5), animal3.getPosition());
        assertEquals(grasses.objectAt(new Vector2d(2, 3)), animal1);
        assertEquals(grasses.objectAt(new Vector2d(6, 5)), animal3);
    }

    @Test
    void isOccupied() {
        GrassField map = new GrassField(0);
        map.placeGrass(new Grass(new Vector2d(2, 2)));
        map.placeGrass(new Grass(new Vector2d(10, 10)));

        Animal animal1 = new Animal(new Vector2d(2, 3));
        Animal animal2 = new Animal(new Vector2d(2, 2));
        Animal animal3 = new Animal(new Vector2d(5, 5));

        try {
            map.place(animal1);
            map.place(animal2);
            map.place(animal3);
        }
        catch (IncorrectPositionException e){
            fail();
        }

        assertTrue(map.isOccupied(new Vector2d(2, 2)));
        assertTrue(map.isOccupied(new Vector2d(10, 10)));
        assertTrue(map.isOccupied(new Vector2d(2, 3)));
        assertTrue(map.isOccupied(new Vector2d(5, 5)));
        assertFalse(map.isOccupied(new Vector2d(7, 7)));
    }

    @Test
    void objectAt() {
        GrassField map = new GrassField(0);

        Grass grass1 = new Grass(new Vector2d(2, 2));
        Grass grass2 = new Grass(new Vector2d(10, 10));
        Grass grass3 = new Grass(new Vector2d(5, 5));
        Grass grass4 = new Grass(new Vector2d(5, 5));

        Animal animal1 = new Animal(new Vector2d(2, 2));
        Animal animal2 = new Animal(new Vector2d(2, 3));

        map.placeGrass(grass1);
        map.placeGrass(grass2);
        map.placeGrass(grass3);
        map.placeGrass(grass4);

        try {
            map.place(animal1);
            map.place(animal2);
        }
        catch (IncorrectPositionException e){
            fail();
        }

        assertEquals(animal1, map.objectAt(new Vector2d(2, 2)));
        assertEquals(grass2, map.objectAt(new Vector2d(10, 10)));
        assertEquals(animal2, map.objectAt(new Vector2d(2, 3)));
        assertEquals(grass4, map.objectAt(new Vector2d(5, 5)));
    }

    @Test
    void canMoveTo() {
        GrassField map = new GrassField(0);

        Grass grass1 = new Grass(new Vector2d(2, 3));
        Grass grass2 = new Grass(new Vector2d(10, 10));

        Animal animal1 = new Animal(new Vector2d(2, 2));
        Animal animal2 = new Animal(new Vector2d(2, 1));
        Animal animal3 = new Animal(new Vector2d(5, 5));

        map.placeGrass(grass1);
        map.placeGrass(grass2);

        try {
            map.place(animal1);
            map.place(animal2);
            map.place(animal3);
        }
        catch (IncorrectPositionException e){
            fail();
        }

        assertTrue(map.canMoveTo(new Vector2d(2, 3)));
        assertFalse(map.canMoveTo(new Vector2d(2, 2)));
        assertFalse(map.canMoveTo(new Vector2d(5, 5)));
        assertTrue(map.canMoveTo(new Vector2d(7, 7)));
        assertTrue(map.canMoveTo(new Vector2d(10, 10)));
    }

    @Test
    void getBounds() {
        GrassField map = new GrassField(0);

        map.placeGrass(new Grass(new Vector2d(1, 2)));
        map.placeGrass(new Grass(new Vector2d(7, 2)));
        map.placeGrass(new Grass(new Vector2d(2, 3)));
        map.placeGrass(new Grass(new Vector2d(5, 8)));
        map.placeGrass(new Grass(new Vector2d(10, 2)));

        try {
            map.place(new Animal(new Vector2d(1, 2)));
            map.place(new Animal(new Vector2d(8, 2)));
            map.place(new Animal(new Vector2d(1, 3)));
            map.place(new Animal(new Vector2d(0, 9)));
        }
        catch (IncorrectPositionException e){
            fail();
        }

        assertEquals(new Boundary(new Vector2d(0, 2), new Vector2d(10,9)),map.getCurrentBound());
    }

    @Test
    void testToString() {
        GrassField map = new GrassField(0);


        map.placeGrass(new Grass(new Vector2d(-3, -3)));
        map.placeGrass(new Grass(new Vector2d(1, 2)));
        map.placeGrass(new Grass(new Vector2d(7, 2)));
        map.placeGrass(new Grass(new Vector2d(2, 3)));
        map.placeGrass(new Grass(new Vector2d(5, 8)));
        map.placeGrass(new Grass(new Vector2d(10, 2)));

        try{
            map.place(new Animal(new Vector2d(1, 2)));
            map.place(new Animal(new Vector2d(8, 2)));
            map.place(new Animal(new Vector2d(1, 3)));
            map.place(new Animal(new Vector2d(0, 9)));
        }
            catch (IncorrectPositionException e){
            fail();
        }

        String excpected = " y\\x -3-2-1 0 1 2 3 4 5 6 7 8 910\r\n" +
                " 10: -----------------------------\r\n" +
                "  9: | | | |N| | | | | | | | | | |\r\n" +
                "  8: | | | | | | | | |*| | | | | |\r\n" +
                "  7: | | | | | | | | | | | | | | |\r\n" +
                "  6: | | | | | | | | | | | | | | |\r\n" +
                "  5: | | | | | | | | | | | | | | |\r\n" +
                "  4: | | | | | | | | | | | | | | |\r\n" +
                "  3: | | | | |N|*| | | | | | | | |\r\n" +
                "  2: | | | | |N| | | | | |*|N| |*|\r\n" +
                "  1: | | | | | | | | | | | | | | |\r\n" +
                "  0: | | | | | | | | | | | | | | |\r\n" +
                " -1: | | | | | | | | | | | | | | |\r\n" +
                " -2: | | | | | | | | | | | | | | |\r\n" +
                " -3: |*| | | | | | | | | | | | | |\r\n" +
                " -4: -----------------------------\r\n";

        assertEquals(excpected, map.toString());
    }

    @Test
    void testGetElements(){
        GrassField map = new GrassField(0);
        Set<WorldElement> elements = new HashSet<>();

        Grass grass1 = new Grass(new Vector2d(2, 3));
        Grass grass2 = new Grass(new Vector2d(3, 3));

        Animal animal1 = new Animal(new Vector2d(0,0));
        Animal animal2 = new Animal(new Vector2d(2, 3));
        Animal animal3 = new Animal(new Vector2d(3, 3));

        elements.add(animal1);
        elements.add(animal2);
        elements.add(animal3);

        elements.add(grass1);
        elements.add(grass2);

        map.placeGrass(grass1);
        map.placeGrass(grass2);

        try{
            map.place(animal1);
            map.place(animal2);
            map.place(animal3);
        }
            catch (IncorrectPositionException e){
            fail();
        }

        Collection<WorldElement> collection = map.getElements();
        assertTrue(elements.containsAll(collection));
    }

    @Test
    void testEmptyMap(){
        GrassField map = new GrassField(0);
        String excpected = " y\\x  0\r\n" +
                "  1: ---\r\n" +
                "  0: | |\r\n" +
                " -1: ---\r\n";

        assertEquals(excpected, map.toString());
    }
}