package agh.ics.oop.model.observers;

import agh.ics.oop.model.WorldMap;

public class ConsoleMapDisplay implements MapChangeListener {
    private int mapChangeCount = 0;

    @Override
    public synchronized void mapChanged(WorldMap worldMap, String message) {
        IO.println("ID mapy:" + worldMap.getId());
        IO.println(message);
        IO.print(worldMap);
        IO.println("dotychczasowa liczba zmian : " + ++mapChangeCount);
        IO.println();
    }
}
