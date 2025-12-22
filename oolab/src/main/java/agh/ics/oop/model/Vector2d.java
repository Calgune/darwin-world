package agh.ics.oop.model;

import java.util.Objects;
import java.util.Random;

public class Vector2d {
    private final int x, y;

    public Vector2d(int x, int y){
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object other){
        if (this == other)
            return true;
        if (!(other instanceof Vector2d))
            return false;

        Vector2d that = (Vector2d) other;
        return (x == that.x && y == that.y);

    }

    public Vector2d opposite(){
        return new Vector2d(-x, -y);
    }

    public Vector2d lowerLeft(Vector2d other){
        return new Vector2d(Math.min(x, other.x), Math.min(y, other.y));
    }

    public Vector2d upperRight(Vector2d other){
        return new Vector2d(Math.max(x, other.x), Math.max(y, other.y));
    }

    public Vector2d subtract(Vector2d other){
        return new Vector2d(x-other.x, y-other.y);
    }

    public Vector2d add(Vector2d other){
        return new Vector2d(x+other.x, y+other.y);
    }

    public boolean follows(Vector2d other){
        return (x >= other.x && y >= other.y);
    }

    public boolean precedes(Vector2d other){
        return (x <= other.x && y <= other.y);
    }

    @Override
    public String toString(){
        return "(" + String.valueOf(x) + "," + String.valueOf(y) + ")";
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public static Vector2d randomPositive(int maxX, int maxY){
        Random random = new Random();
        return new Vector2d(random.nextInt(0,maxX+1), random.nextInt(0,maxY+1));
    }
}
