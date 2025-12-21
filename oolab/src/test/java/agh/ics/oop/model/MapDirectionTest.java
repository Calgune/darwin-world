package agh.ics.oop.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MapDirectionTest {

    @Test
    void next() {
        MapDirection dirNorth = MapDirection.NORTH;
        MapDirection dirEast = MapDirection.EAST;
        MapDirection dirSouth = MapDirection.SOUTH;
        MapDirection dirWest = MapDirection.WEST;

        assertEquals(MapDirection.EAST, dirNorth.next());
        assertEquals(MapDirection.SOUTH, dirEast.next());
        assertEquals(MapDirection.WEST, dirSouth.next());
        assertEquals(MapDirection.NORTH, dirWest.next());
    }

    @Test
    void previous() {
        MapDirection dirNorth = MapDirection.NORTH;
        MapDirection dirEast = MapDirection.EAST;
        MapDirection dirSouth = MapDirection.SOUTH;
        MapDirection dirWest = MapDirection.WEST;

        assertEquals(MapDirection.WEST, dirNorth.previous());
        assertEquals(MapDirection.SOUTH, dirWest.previous());
        assertEquals(MapDirection.EAST, dirSouth.previous());
        assertEquals(MapDirection.NORTH, dirEast.previous());
    }
}