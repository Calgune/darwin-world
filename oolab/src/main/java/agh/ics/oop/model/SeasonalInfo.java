package agh.ics.oop.model;

import agh.ics.oop.model.util.Season;

public interface SeasonalInfo {
    Season getSeason();
    double getTemperature();
}
