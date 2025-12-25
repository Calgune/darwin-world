package agh.ics.oop;

import agh.ics.oop.model.util.MapSettings;

public class SeasonManager {
    private int summerLengthInDays;
    private int winterLengthInDays;
    private double minimalTemperatureInWinter;
    private int distanceToTransferHeat;
    private int minimalAnimalCountInRangeToTransferHeat;
    private int energyDrainReduction;
    private double baseTemperature;
    private int seasonLengthInDays;
    private double mappedDayParameter;

    private double energyFromGrassPenaltyParameter;
    private double grassSpawnPenaltyParameter;
    private double energyDecayPenaltyParameter;


    public SeasonManager(MapSettings settings){
        this.summerLengthInDays = settings.getSummerLengthInDays();
        this.winterLengthInDays = settings.getWinterLengthInDays();
        this.minimalTemperatureInWinter = settings.getMinimalTemperatureInWinter();
        this.distanceToTransferHeat = settings.getDistanceToTransferHeat();
        this.minimalAnimalCountInRangeToTransferHeat = settings.getMinimalAnimalCountInRangeToTransferHeat();
        this.energyDrainReduction = settings.getEnergyDrainReduction();
        baseTemperature = 20;
        seasonLengthInDays = summerLengthInDays + winterLengthInDays;
        mappedDayParameter = 2 * Math.PI / (double) seasonLengthInDays;

        // paramtry funkcji opisane w desmos
        energyFromGrassPenaltyParameter = mapValue(minimalTemperatureInWinter, -40.f, 20, 0.75, 0.5); // k : (0,75 -  0.5)
        grassSpawnPenaltyParameter = mapValue(minimalTemperatureInWinter, 0.f, 20, 1, 0.5); // k : (1 -  0.5)
        energyDecayPenaltyParameter = mapValue(minimalTemperatureInWinter, -40.f, 20, 4, 0); // T : (4 -  0)
    }

    private static double mapValue(double val, double minIn, double maxIn, double minOut, double maxOut){
        double clampedVal = Math.max(minIn, Math.min(val, maxIn));
        return ((clampedVal - minIn) / (maxIn - minIn)) * (maxOut - minOut) + minOut;
    }

    // zwraca [0 - 1] mnoży energie pozyskaną z trawy
    public double getEnergyFromGrassModifier(int day){
        if (isSummer(day)) return 1.f;
        double x = Math.cos(day*mappedDayParameter);
        return ((-x + 1.f) / 2.f * energyFromGrassPenaltyParameter) + x;
    }

    // zwraca [0,33 - 1]
    public double getGrassSpawnModifier(int day){
        if (isSummer(day)) return 1.f;
        double x = Math.cos(day*mappedDayParameter);
        return ((-x + 1.f) / 2.f * energyFromGrassPenaltyParameter) + x;
    }

    // zwraca [1 - 4] mnoży spadek energi
    public double getEnergyDecayModifier(int day){
        if (isSummer(day)) return 1.f;
        double x = Math.cos(day*mappedDayParameter);
        return energyDecayPenaltyParameter * ((x + 1.f) / 2.f - x) + 1;
    }

    // lato jest zawsze pierwsze
    private boolean isSummer(int day){
        int seasonDay = day % seasonLengthInDays;
        return seasonDay < summerLengthInDays;
    }


}
