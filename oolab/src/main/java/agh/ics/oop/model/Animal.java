package agh.ics.oop.model;
import java.security.PrivilegedExceptionAction;
import java.util.ArrayList;
import java.util.Objects;

public class Animal implements WorldElement {
    private MapDirection direction;
    private Vector2d position;
    private int energy;
    private int age;
    private int kidsCount;
    private ArrayList<Integer> genomes;

    public Animal(){
        this(new Vector2d(2,2));
    }

    public Animal(Vector2d position){
        this.position = position;
        direction = MapDirection.NORTH;
    }

    @Override
    public String toString(){
        return  switch (direction){
            case NORTH -> "N";
            case SOUTH -> "S";
            case EAST -> "E";
            case WEST -> "W";
        };
    }

    public boolean isAt(Vector2d position){
        return Objects.equals(this.position, position);
    }

    public void move(MoveDirection direction, MoveValidator moveValidator){
        switch (direction){
            case RIGHT -> this.direction = this.direction.next();
            case LEFT -> this.direction = this.direction.previous();
            case BACKWARDS, FORWARD -> {
                Vector2d move = this.direction.toUnitVector();
                if (direction == MoveDirection.BACKWARDS)
                    move = move.opposite();

                Vector2d positionAfterMove = this.position.add(move);

                if(moveValidator.canMoveTo(positionAfterMove)){
                    this.position = positionAfterMove;
                }
            }
        }
    }

    public void eat(){

    }

    @Override
    public Vector2d getPosition(){
        return position;
    }

    public MapDirection getDirection() {
        return direction;
    }


    public boolean hasStarved(){
        return energy <= 0;
    }

    public int getEnergy() {
        return energy;
    }

    public int getAge() {
        return age;
    }

    public int getKidsCount() {
        return kidsCount;
    }

    public ArrayList<Integer> getGenomes() {
        return genomes;
    }
}
