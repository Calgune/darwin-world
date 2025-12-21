package agh.ics.oop;

import agh.ics.oop.model.MoveDirection;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OptionsParserTest {

    @Test
    void parse() {
        String[] args = new String[] {"l", "x", "r", "f", "l", "x", "x", "b"};

        List<MoveDirection> moveDirections = List.of(
                MoveDirection.LEFT, MoveDirection.RIGHT,
                MoveDirection.FORWARD, MoveDirection.LEFT,
                MoveDirection.BACKWARDS);

        assertEquals(moveDirections, OptionsParser.parse(args));
    }
}