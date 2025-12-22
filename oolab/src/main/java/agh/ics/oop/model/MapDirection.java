package agh.ics.oop.model;

public enum MapDirection {
    NORTH(0), NORTH_EAST(1), EAST(2), SOUTH_EAST(3),
    SOUTH(4), SOUTH_WEST(5), WEST(6), NORTH_WEST(7);

    private final int value;

    MapDirection(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public MapDirection changeDirection(int steps) {
        MapDirection[] values = MapDirection.values();
        int nextIndex = (this.getValue() + steps) % 8;
        return values[nextIndex];
    }

    public String toString(){
        return switch (this) {
            case NORTH -> "polnoc";
            case NORTH_EAST -> "polnocny-wschod";
            case EAST -> "wschod";
            case SOUTH_EAST -> "poludniowy-wschod";
            case SOUTH -> "poludnie";
            case SOUTH_WEST -> "poludniowy-zachod";
            case WEST -> "zachod";
            case NORTH_WEST -> "polnocny-zachod";
        };
    }

    public Vector2d toUnitVector() {
        return switch (this) {
            case NORTH -> new Vector2d(0, 1);
            case NORTH_EAST -> new Vector2d(1, 1);
            case EAST -> new Vector2d(1, 0);
            case SOUTH_EAST -> new Vector2d(1, -1);
            case SOUTH -> new Vector2d(0, -1);
            case SOUTH_WEST -> new Vector2d(-1, -1);
            case WEST -> new Vector2d(-1, 0);
            case NORTH_WEST -> new Vector2d(-1, 1);
        };
    }

    public MapDirection bounceOfTheWall() {
        return switch (this) {
            case NORTH -> SOUTH;
            case NORTH_EAST -> SOUTH_EAST;
            case EAST -> EAST;
            case SOUTH_EAST -> NORTH_EAST;
            case SOUTH -> NORTH;
            case SOUTH_WEST -> NORTH_WEST;
            case WEST -> WEST;
            case NORTH_WEST -> SOUTH_WEST;
        };
    }
}
