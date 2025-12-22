package agh.ics.oop.model;

public enum MoveDirection {
    N(0), NE(1), E(2), SE(3),
    S(4), SW(5), W(6), NW(7);

    private final int value;

    MoveDirection(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

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
            default -> throw new IllegalStateException("Niepoprawna wartosc. Prosze uzyc 0-7");
        };
    }
}