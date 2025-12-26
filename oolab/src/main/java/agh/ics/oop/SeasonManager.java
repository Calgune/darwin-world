package agh.ics.oop;

import agh.ics.oop.model.util.MapSettings;
import agh.ics.oop.model.util.Season;

// można poprawić potem aby była jedna fukcje licząca poprostu temperature, a na jej podstawie (mapując) liczyć modifikatory rośnięcia itp.
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
    private double temperatureParameter;


    public SeasonManager(MapSettings settings){
        this.summerLengthInDays = settings.getSummerLengthInDays();
        this.winterLengthInDays = settings.getWinterLengthInDays();
        this.minimalTemperatureInWinter = settings.getMinimalTemperatureInWinter();
        this.distanceToTransferHeat = settings.getDistanceToTransferHeat();
        this.minimalAnimalCountInRangeToTransferHeat = settings.getMinimalAnimalCountInRangeToTransferHeat();
        this.energyDrainReduction = settings.getEnergyDrainReduction();
        baseTemperature = 20;
        seasonLengthInDays = summerLengthInDays + winterLengthInDays;
        mappedDayParameter = 2 * Math.PI / (double) winterLengthInDays;

        // paramtry funkcji opisane w desmos
        energyFromGrassPenaltyParameter = mapValue(minimalTemperatureInWinter, -40.f, 20, 0.75, 0.5); // k : (0,75 -  0.5)
        temperatureParameter = energyFromGrassPenaltyParameter;
        grassSpawnPenaltyParameter = mapValue(minimalTemperatureInWinter, 0.f, 20, 1, 0.5); // k : (1 -  0.5)
        energyDecayPenaltyParameter = mapValue(minimalTemperatureInWinter, -40.f, 20, 4, 0); // T : (4 -  0)

        IO.println(energyFromGrassPenaltyParameter);
        IO.println(grassSpawnPenaltyParameter);
        IO.println(energyDecayPenaltyParameter);
    }

    private static double mapValue(double val, double minIn, double maxIn, double minOut, double maxOut){
        double clampedVal = Math.max(minIn, Math.min(val, maxIn));
        return ((clampedVal - minIn) / (maxIn - minIn)) * (maxOut - minOut) + minOut;
    }

    // zwraca [0.25 - 1] mnoży energie pozyskaną z trawy
    // dziala
    public double getEnergyFromGrassModifier(int day){
        if (isSummer(day)) return 1.f;
        int seasonDay = day % seasonLengthInDays - summerLengthInDays;
        double x = Math.cos(seasonDay*mappedDayParameter);
        return ((-x + 1.f) / (2.f * energyFromGrassPenaltyParameter)) + x;
    }

    // zwraca [0 - 1]
    public double getGrassSpawnModifier(int day){
        if (isSummer(day)) return 1.f;
        int seasonDay = day % seasonLengthInDays - summerLengthInDays;
        double x = Math.cos(seasonDay*mappedDayParameter);
        return ((-x + 1.f) / (2.f * grassSpawnPenaltyParameter)) + x;
    }

    double getMinimalTemperatureInWinterNormalized(int day){
        if (isSummer(day)) return 1.f;
        int seasonDay = day % seasonLengthInDays - summerLengthInDays;
        double x = Math.cos(seasonDay*mappedDayParameter);
        return ((-x + 1.f) / (2.f)) + x;
    }

    // zwraca [1 - 4] mnoży spadek energi
    public double getEnergyDecayModifier(int day){
        if (isSummer(day)) return 1.f;
        int seasonDay = day % seasonLengthInDays - summerLengthInDays;
        double x = Math.cos(seasonDay*mappedDayParameter);
        return energyDecayPenaltyParameter * ((x + 1.f) / 2.f - x) + 1;
    }

    // lato jest zawsze pierwsze
    private boolean isSummer(int day){
        int seasonDay = day % seasonLengthInDays;
        return seasonDay < summerLengthInDays;
    }

    double getTemperature(int day){
        return mapValue(getMinimalTemperatureInWinterNormalized(day), 0.f, 1.f, minimalTemperatureInWinter, baseTemperature);
    }

    double getTemperatureToColdnessFactor(int day){
        return mapValue(getTemperature(day), -20, 20, 1.f, 0.f);
    }

    Season getSeason(int day){
        if(isSummer(day))
            return Season.Summer;
        return Season.Winter;
    }
}
