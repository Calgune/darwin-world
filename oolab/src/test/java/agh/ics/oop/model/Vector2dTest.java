package agh.ics.oop.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Vector2dTest {

    @Test
    void testEquals() {
        Vector2d v1 = new Vector2d(1,2);
        Vector2d v2 = new Vector2d(1,2);
        Vector2d v3 = new Vector2d(9,3);

        Object object = new Object();

        assertFalse(v1.equals(object));
        assertTrue(v1.equals(v2));
        assertFalse(v1.equals(v3));

    }

    @Test
    void opposite() {
        // given
        Vector2d v1 = new Vector2d(2, -1);
        Vector2d v2 = new Vector2d(0, 0);

        // when
        v1 = v1.opposite();
        v2 = v2.opposite();

        // then
        assertEquals("(-2,1)",v1.toString());
        assertEquals("(0,0)",v2.toString());

    }

    @Test
    void lowerLeft() {
        // given
        Vector2d v1 = new Vector2d(2, -1);
        Vector2d v2 = new Vector2d(0, 0);

        // when
        Vector2d v3 = v1.lowerLeft(v2);

        // then
        assertEquals("(0,-1)",v3.toString());
    }

    @Test
    void upperRight() {
        // given
        Vector2d v1 = new Vector2d(2, -1);
        Vector2d v2 = new Vector2d(1, 7);

        // when
        Vector2d v3 = v1.upperRight(v2);

        // then
        assertEquals("(2,7)",v3.toString());
    }

    @Test
    void subtract() {
        // given
        Vector2d v1 = new Vector2d(2, -1);
        Vector2d v2 = new Vector2d(1, 7);

        // when
        Vector2d v3 = v1.subtract(v2);

        // then
        assertEquals("(1,-8)",v3.toString());

    }

    @Test
    void add() {
        // given
        Vector2d v1 = new Vector2d(2, -1);
        Vector2d v2 = new Vector2d(1, 7);

        // when
        Vector2d v3 = v1.add(v2);

        // then
        assertEquals("(3,6)",v3.toString());
    }

    @Test
    void follows() {
        Vector2d v1 = new Vector2d(2, -1);
        Vector2d v2 = new Vector2d(1, 7);
        Vector2d v3 = new Vector2d(14, 27);

        assertFalse(v1.follows(v2));
        assertTrue(v3.follows(v1));
        assertTrue(v2.follows(v2));
    }

    @Test
    void precedes() {
        Vector2d v1 = new Vector2d(2, -1);
        Vector2d v2 = new Vector2d(1, 7);
        Vector2d v3 = new Vector2d(14, 27);

        assertFalse(v1.precedes(v2));
        assertFalse(v3.precedes(v1));
        assertTrue(v2.precedes(v2));
    }

    @Test
    void testToString() {
        // given
        Vector2d v1 = new Vector2d(2, -1);

        // when
        String vec_str = v1.toString();

        // then
        assertEquals("(2,-1)", vec_str);
    }
}