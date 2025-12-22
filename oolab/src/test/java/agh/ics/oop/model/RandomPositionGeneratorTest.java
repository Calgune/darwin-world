package agh.ics.oop.model;

import org.junit.jupiter.api.Test;

import javax.swing.text.Position;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class RandomPositionGeneratorTest {
    @Test
    void testHasDuplicates(){
        RandomPositionGenerator generator = new RandomPositionGenerator(new Boundary(new Vector2d(500, 500), new Vector2d(1000, 1000)), 10000);
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
        RandomPositionGenerator generator = new RandomPositionGenerator(new Boundary(new Vector2d(500, 500), new Vector2d(1000, 1000)), 10000);

        int count = 0;
        for(Vector2d position : generator)
            count++;

        assertEquals(10000, count);
    }

    @Test
    void testAllGeneratedValuesInRange(){
        Boundary boundary = new Boundary(new Vector2d(-500, -500), new Vector2d(1000, 1000));
        RandomPositionGenerator generator = new RandomPositionGenerator(boundary, 10000);

        int count = 0;
        for(Vector2d position : generator)
            if( position.precedes(boundary.upperRight())
                    && position.follows(boundary.lowerLeft())){
                count++;
            }

        assertEquals(10000, count);
    }
}