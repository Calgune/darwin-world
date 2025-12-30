package agh.ics.oop.model.simulation;

import agh.ics.oop.MapVisualState;
import agh.ics.oop.model.observers.SimulationChangeListener;
import agh.ics.oop.model.observers.StatisticsTracker;
import agh.ics.oop.model.util.WriteToCSV;

public interface Simulation extends Runnable {
    void addObserver(SimulationChangeListener observer);
    void removeObserver(SimulationChangeListener observer);

    void killSimulation();

    WriteToCSV getCSV();

    void startStop();
    int getDay();
    MapVisualState getMapVisualState();
    boolean isPaused();

    StatisticsTracker getStatistics();

    @Override
    void run();
    public void runOneStep();
}