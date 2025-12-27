package agh.ics.oop.model.util;

import agh.ics.oop.model.Boundary;
import agh.ics.oop.model.Vector2d;

import java.util.*;

// implementuje
// Fisher–Yates shuffle Algorithm

public class RandomPositionGenerator implements Iterable<Vector2d>{
    private final Map<Integer, Integer> swapped = new HashMap<>();
    private int remaining;
    final private int width;
    final private int grassCount;
    final private Boundary boundary;

    public RandomPositionGenerator(Boundary boundary, int grassCount){
        this.boundary = boundary;

        this.width = boundary.upperRight().getX() - boundary.lowerLeft().getX() + 1;
        int height = boundary.upperRight().getY() - boundary.lowerLeft().getY() + 1;

        this.grassCount = grassCount;
        remaining = width * height;
    }

    private int getSwappedOrDefault(int i){
        if(swapped.containsKey(i))
            return swapped.get(i);
        return i;
    }

    @Override
    public Iterator<Vector2d> iterator() {
        return new Iterator<Vector2d>() {
            private final Random random = new Random();
            private int generated = 0;

            @Override
            public boolean hasNext() {
                return remaining > 0 && generated < grassCount;
            }

            @Override
            public Vector2d next() {
                int i = random.nextInt(remaining);
                int mapped = getSwappedOrDefault(i);
                int lastMapped = getSwappedOrDefault(remaining-1);

                swapped.put(i, lastMapped);
                swapped.put(remaining-1, mapped);

                generated++;
                remaining--;

                int x = mapped % width + boundary.lowerLeft().getX();
                int y = mapped / width + boundary.lowerLeft().getY();

                return new Vector2d(x, y);
            }
        };
    }
}
