package agh.ics.oop.model.util;

public class Utility {
    public static double mapValue(double val, double minIn, double maxIn, double minOut, double maxOut){
        double clampedVal = Math.max(minIn, Math.min(val, maxIn));
        return ((clampedVal - minIn) / (maxIn - minIn)) * (maxOut - minOut) + minOut;
    }
}
