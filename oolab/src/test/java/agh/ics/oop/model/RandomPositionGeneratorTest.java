package agh.ics.oop.model;

import org.junit.jupiter.api.Test;

import javax.swing.text.Position;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class RandomPositionGeneratorTest {
    @Test
    void testHasDuplicates(){
        RandomPositionGenerator generator = new RandomPositionGenerator(10, 10, 100);
        Iterator<Vector2d> iterator = generator.iterator();
        Set<Vector2d> seen = new HashSet<>();
        boolean hasDuplicates = false;

        while (iterator.hasNext()){
            Vector2d position = iterator.next();
            if (!seen.add(position)){
                hasDuplicates = true;
            }
        }
        assertFalse(hasDuplicates);
    }
    @Test
    void testGenerateEnough(){
        RandomPositionGenerator generator = new RandomPositionGenerator(10, 10, 100);

        int count = 0;
        for(Vector2d position : generator)
            count++;

        assertEquals(100, count);
    }
}