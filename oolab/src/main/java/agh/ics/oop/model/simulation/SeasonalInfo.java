package agh.ics.oop.model.simulation;

import agh.ics.oop.model.util.Season;

public interface SeasonalInfo {
    Season getSeason();

    void updateStatistics();

    double getTemperature();
}
