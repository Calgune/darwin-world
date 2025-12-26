package agh.ics.oop.model;

import agh.ics.oop.MapVisualState;
import agh.ics.oop.model.observers.SimulationObserver;
import agh.ics.oop.model.observers.StatisticsTracker;
import javafx.scene.paint.Color;

public interface Simulation extends  Runnable{
    void addObserver(SimulationObserver observer);
    void removeObserver(SimulationObserver observer);
    void startStop();
    int getDay();
    MapVisualState getMapVisualState();

    @Override
    void run();
}
