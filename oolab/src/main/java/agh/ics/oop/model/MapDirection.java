package agh.ics.oop.model;

public enum MapDirection {
    NORTH,
    NORTH_EAST,
    EAST,
    SOUTH_EAST,
    SOUTH,
    SOUTH_WEST,
    WEST,
    NORTH_WEST;

    public String toString(){
        return switch (this) {
            case NORTH -> "Polnoc";
            case NORTH_EAST -> "Polnocny-wschod";
            case EAST -> "Wschod";
            case SOUTH_EAST -> "Poludniowy-wschod";
            case SOUTH -> "Poludnie";
            case SOUTH_WEST -> "Poludniowy-zachod";
            case WEST -> "Zachod";
            case NORTH_WEST -> "Polnocny-zachod";
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

//    public MapDirection next() {
//        return switch (this) {
//            case NORTH -> NORTH_EAST;
//            case NORTH_EAST -> EAST;
//            case EAST -> SOUTH_EAST;
//            case SOUTH_EAST -> SOUTH;
//            case SOUTH -> SOUTH_WEST;
//            case SOUTH_WEST -> WEST;
//            case WEST -> NORTH_WEST;
//            case NORTH_WEST -> NORTH;
//        };
//    }
//
//    public MapDirection previous() {
//        return switch (this) {
//            case NORTH -> NORTH_WEST;
//            case NORTH_WEST -> WEST;
//            case WEST -> SOUTH_WEST;
//            case SOUTH_WEST -> SOUTH;
//            case SOUTH -> SOUTH_EAST;
//            case SOUTH_EAST -> EAST;
//            case EAST -> NORTH_EAST;
//            case NORTH_EAST -> NORTH;
//        };
//    }
}
