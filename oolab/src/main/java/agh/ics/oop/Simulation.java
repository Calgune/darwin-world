package agh.ics.oop;

import agh.ics.oop.model.*;
import agh.ics.oop.model.observers.SimulationObserver;
import agh.ics.oop.model.util.MapSettings;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class Simulation extends AbstractSimulation {
    public Simulation(WorldMap map, MapSettings settings) {
        super(map, settings);
    }
}




