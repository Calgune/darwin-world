package agh.ics.oop.model.observers;

import agh.ics.oop.model.WorldMap;

public interface MapChangeListener {
    void mapChanged(WorldMap worldMap, String message);
}
