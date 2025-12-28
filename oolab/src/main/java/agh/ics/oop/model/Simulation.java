package agh.ics.oop.model;

import agh.ics.oop.MapVisualState;
import agh.ics.oop.model.observers.SimulationChangeListener;
import agh.ics.oop.model.observers.StatisticsTracker;

public interface Simulation extends  Runnable{
    void addObserver(SimulationChangeListener observer);
    void removeObserver(SimulationChangeListener observer);

    StatisticsTracker getStatistics();

    void killSimulation();

    void startStop();
    int getDay();
    MapVisualState getMapVisualState();

    @Override
    void run();
}
