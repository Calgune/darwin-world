package agh.ics.oop;

import agh.ics.oop.model.*;
import agh.ics.oop.model.maps.NormalVisualState;
import agh.ics.oop.model.util.MapSettings;

public class NormalSimulation extends AbstractSimulation {
    public NormalSimulation(WorldMap map, MapSettings settings) {
        super(map, settings);
    }

    @Override
    public MapVisualState getMapVisualState() {
        return new NormalVisualState();
    }
}




