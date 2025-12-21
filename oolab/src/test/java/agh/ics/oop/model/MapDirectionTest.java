package agh.ics.oop.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MapDirectionTest {
    @Test
    void testToString() {
        assertEquals("Polnoc", MapDirection.NORTH.toString());
        assertEquals("Polnocny-wschod", MapDirection.NORTH_EAST.toString());
        assertEquals("Wschod", MapDirection.EAST.toString());
        assertEquals("Poludniowy-wschod", MapDirection.SOUTH_EAST.toString());
        assertEquals("Poludnie", MapDirection.SOUTH.toString());
        assertEquals("Poludniowy-zachod", MapDirection.SOUTH_WEST.toString());
        assertEquals("Zachod", MapDirection.WEST.toString());
        assertEquals("Polnocny-zachod", MapDirection.NORTH_WEST.toString());
    }

    @Test
    void toUnitVector() {
        assertEquals(new Vector2d(0, 1), MapDirection.NORTH.toUnitVector());
        assertEquals(new Vector2d(1, 1), MapDirection.NORTH_EAST.toUnitVector());
        assertEquals(new Vector2d(1, 0), MapDirection.EAST.toUnitVector());
        assertEquals(new Vector2d(1, -1), MapDirection.SOUTH_EAST.toUnitVector());
        assertEquals(new Vector2d(0, -1), MapDirection.SOUTH.toUnitVector());
        assertEquals(new Vector2d(-1, -1), MapDirection.SOUTH_WEST.toUnitVector());
        assertEquals(new Vector2d(-1, 0), MapDirection.WEST.toUnitVector());
        assertEquals(new Vector2d(-1, 1), MapDirection.NORTH_WEST.toUnitVector());
    }
}