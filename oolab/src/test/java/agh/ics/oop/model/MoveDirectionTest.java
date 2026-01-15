package agh.ics.oop.model;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class MoveDirectionTest {
    @Test
    void shouldReturnCorrectDirection() {
        assertEquals(MoveDirection.N, MoveDirection.fromString("0"));
        assertEquals(MoveDirection.NE, MoveDirection.fromString("1"));
        assertEquals(MoveDirection.E, MoveDirection.fromString("2"));
        assertEquals(MoveDirection.SE, MoveDirection.fromString("3"));
        assertEquals(MoveDirection.S, MoveDirection.fromString("4"));
        assertEquals(MoveDirection.SW, MoveDirection.fromString("5"));
        assertEquals(MoveDirection.W, MoveDirection.fromString("6"));
        assertEquals(MoveDirection.NW, MoveDirection.fromString("7"));
    }

    @Test
    void shouldThrowExceptionForInvalidInput() {
        assertThrows(IllegalStateException.class, () -> {
            MoveDirection.fromString("8");
        });
        assertThrows(IllegalStateException.class, () -> {
            MoveDirection.fromString("xyz");
        });
    }
}
