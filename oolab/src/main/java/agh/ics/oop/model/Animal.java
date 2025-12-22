package agh.ics.oop.model;
import java.util.ArrayList;
import java.util.Objects;

public class Animal implements WorldElement {
    private MapDirection direction;
    private Vector2d position;
    // narazie
    private int energy = 1;
    private int age;
    private int kidsCount;
    private final Genotype genotype;

    public Animal(Vector2d position, Genotype genes){
        this.position = position;
        direction = MapDirection.NORTH; // zwierzak przed kaxdym ruchem i tak najpierw
        // sie obraca. moze byc tutaj dowolna wartosc

//        this.direction = MapDirection.values()[(int) (Math.random() * 8)];

        this.genotype = genes;
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

    public void move(MoveValidator moveValidator) {
        int rotation = this.genotype.todaysMove();
        this.direction = this.direction.changeDirection(rotation);
        Vector2d moveVector = this.direction.toUnitVector();
        Vector2d positionAfterMove = this.position.add(moveVector);

        if (moveValidator.canMoveTo(positionAfterMove)) {
            this.position = moveValidator.nextPosition(positionAfterMove);
        } else {
            this.direction = this.direction.bounceOfTheWall();
            Vector2d newMoveVector = this.direction.toUnitVector();
            Vector2d newPositionAfterMove = this.position.add(newMoveVector);
            this.position = moveValidator.nextPosition(newPositionAfterMove);
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
}
