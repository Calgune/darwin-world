package agh.ics.oop.model;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class MoveDirectionTest {
    @Test
    void shouldReturnCorrectDirection() {
        assertEquals(MoveDirection.N, MoveDirection.fromString("0"));
        assertEquals(MoveDirection.S, MoveDirection.fromString("4"));
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
