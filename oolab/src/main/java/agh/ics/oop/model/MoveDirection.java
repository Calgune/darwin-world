package agh.ics.oop.model;

public enum MoveDirection {
    N,
    NE,
    E,
    SE,
    S,
    SW,
    W,
    NW;

    public static MoveDirection fromString(String s) {
        return switch (s) {
            case "0" -> N;
            case "1" -> NE;
            case "2" -> E;
            case "3" -> SE;
            case "4" -> S;
            case "5" -> SW;
            case "6" -> W;
            case "7" -> NW;
            default -> throw new IllegalStateException("Incorrect value. Please use 0-7");
        }; //trzeba to potem zlapac
    }
}