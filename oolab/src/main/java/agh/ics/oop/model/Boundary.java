package agh.ics.oop.model;

public record Boundary(Vector2d lowerLeft, Vector2d upperRight)  {
    public int getCellsCount(){
        return (upperRight.getX() - lowerLeft.getX() + 1) * (upperRight.getY() - lowerLeft.getY() + 1);
    }
}
