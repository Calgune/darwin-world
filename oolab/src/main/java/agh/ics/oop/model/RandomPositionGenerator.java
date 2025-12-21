package agh.ics.oop.model;

import java.util.*;
import java.util.function.Consumer;

// implementuje
// Fisher–Yates shuffle Algorithm

public class RandomPositionGenerator implements Iterable<Vector2d>{
    private final Map<Integer, Integer> swapped = new HashMap<>();
    private int remaining;
    final private int maxWidth;
    final private int grassCount;

    public RandomPositionGenerator(int maxWidth, int maxHeight, int grassCount){
        remaining = maxHeight * maxWidth;
        this.maxWidth = maxWidth;
        this.grassCount = grassCount;
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

                int x = mapped % maxWidth;
                int y = mapped / maxWidth;

                swapped.put(i, lastMapped);
                swapped.put(remaining-1, mapped);

                generated++;
                remaining--;

                return new Vector2d(x, y);
            }
        };
    }
}
