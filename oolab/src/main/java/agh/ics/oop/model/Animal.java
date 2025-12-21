package agh.ics.oop.model;
import java.util.Objects;

public class Animal implements WorldElement {
    private MapDirection direction;
    private Vector2d position;

    public Animal(Vector2d position){
        this.position = position;
        direction = MapDirection.NORTH; // !!! trzeba bedzie pobierac genotyp. juz nie ma tak latwo
    }

    @Override
    public String toString(){
        return  switch (direction){
            case NORTH -> "N";
            case SOUTH -> "S";
            case NORTH_EAST -> "NE";
            case EAST -> "E";
            case WEST -> "W";
            case SOUTH_EAST -> "SE";
            case SOUTH_WEST -> "SW";
            case NORTH_WEST -> "NW";
        };
    }

    public boolean isAt(Vector2d position){
        return Objects.equals(this.position, position);
    }

    public void move(MoveDirection direction, MoveValidator moveValidator) {
        switch (direction) {
            case N  -> this.direction = MapDirection.NORTH;
            case NE -> this.direction = MapDirection.NORTH_EAST;
            case E  -> this.direction = MapDirection.EAST;
            case SE -> this.direction = MapDirection.SOUTH_EAST;
            case S  -> this.direction = MapDirection.SOUTH;
            case SW -> this.direction = MapDirection.SOUTH_WEST;
            case W  -> this.direction = MapDirection.WEST;
            case NW -> this.direction = MapDirection.NORTH_WEST;
        }

        Vector2d moveVector = this.direction.toUnitVector();
        Vector2d positionAfterMove = this.position.add(moveVector);

        if (moveValidator.canMoveTo(positionAfterMove)) {
            this.position = positionAfterMove;
        }
    }

    @Override
    public Vector2d getPosition(){
        return position;
    }

    public MapDirection getDirection() {
        return direction;
    }
}
