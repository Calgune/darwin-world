package agh.ics.oop.model;

import agh.ics.oop.model.util.MapVisualizer;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RectangularMap extends AbstractWorldMap{
    private final Boundary boundary;

    public RectangularMap(int width, int height){
        this.boundary = new Boundary(new Vector2d(0,0), new Vector2d(width-1, height-1));
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
       return position.precedes(boundary.upperRight()) &&
                position.follows(boundary.lowerLeft()) &&
                super.canMoveTo(position);
    }

    @Override
    public Boundary getCurrentBound() {
        return boundary;
    }

}
