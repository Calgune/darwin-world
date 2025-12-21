package agh.ics.oop.model;

import agh.ics.oop.model.util.MapSettings;

public class CylindricalMap extends AbstractWorldMap{
    public CylindricalMap(MapSettings settings) {
        super(settings);
    }

    public boolean canMoveTo(Vector2d position) {
        Boundary boundary = getBoundary();
        return position.precedes(boundary.upperRight()) &&
                position.follows(boundary.lowerLeft()) &&
                super.canMoveTo(position);
    }
}
