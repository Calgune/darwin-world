package agh.ics.oop.model.observers;

import agh.ics.oop.model.Animal;
import agh.ics.oop.model.WorldMap;

public interface SimulationObserver {
    void updateStats(WorldMap map);
    void registerDeath(Animal animal);
}
