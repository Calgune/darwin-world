package agh.ics.oop.model.util;

public class ChartData {
    int day;
    int animals;
    int grasses;
    double averageEnergy;
    double averageLifeSpan;
    double averageKidsCount;

    public ChartData(int day, int animals, int grasses, double averageEnergy, double averageLifeSpan, double averageKidsCount){
        this.day = day;
        this.animals = animals;
        this.grasses = grasses;
        this.averageEnergy = averageEnergy;
        this.averageLifeSpan = averageLifeSpan;
        this.averageKidsCount = averageKidsCount;
    }

    public int getDay() {
        return day;
    }

    public int getAnimals() {
        return animals;
    }

    public int getGrasses() {
        return grasses;
    }

    public double getAverageEnergy() {
        return averageEnergy;
    }

    public double getAverageLifeSpan() {
        return averageLifeSpan;
    }

    public double getAverageKidsCount() {
        return averageKidsCount;
    }
}
