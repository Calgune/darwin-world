package agh.ics.oop;

import agh.ics.oop.model.MoveDirection;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OptionsParserTest {

    @Test
    void parse() {
        String[] args = new String[] {"1", "7", "6", "2", "0", "6", "4"};

        List<MoveDirection> moveDirections = List.of(
                MoveDirection.NE, MoveDirection.NW,
                MoveDirection.W, MoveDirection.E,
                MoveDirection.N, MoveDirection.W,
                MoveDirection.S);

        assertEquals(moveDirections, OptionsParser.parse(args));
    }
}