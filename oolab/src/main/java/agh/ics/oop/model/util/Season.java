package agh.ics.oop.model.util;

public enum Season {
    Summer, Winter;

    @Override
    public String toString() {
        return switch (this){
            case Summer -> "lato";
            case Winter -> "zima";
        };
    }
}
