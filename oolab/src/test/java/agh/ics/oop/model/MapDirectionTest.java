package agh.ics.oop.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MapDirectionTest {
    @Test
    void testToString() {
        assertEquals("polnoc", MapDirection.NORTH.toString());
        assertEquals("polnocny-wschod", MapDirection.NORTH_EAST.toString());
        assertEquals("wschod", MapDirection.EAST.toString());
        assertEquals("poludniowy-wschod", MapDirection.SOUTH_EAST.toString());
        assertEquals("poludnie", MapDirection.SOUTH.toString());
        assertEquals("poludniowy-zachod", MapDirection.SOUTH_WEST.toString());
        assertEquals("zachod", MapDirection.WEST.toString());
        assertEquals("polnocny-zachod", MapDirection.NORTH_WEST.toString());
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