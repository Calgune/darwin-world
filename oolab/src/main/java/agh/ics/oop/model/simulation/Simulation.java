package agh.ics.oop.model.simulation;

import agh.ics.oop.MapVisualState;
import agh.ics.oop.model.observers.SimulationObserver;

public interface Simulation extends  Runnable{
    void addObserver(SimulationObserver observer);
    void removeObserver(SimulationObserver observer);

    void killSimulation();

    void startStop();
    int getDay();
    MapVisualState getMapVisualState();

    @Override
    void run();

    boolean isPaused();
}
