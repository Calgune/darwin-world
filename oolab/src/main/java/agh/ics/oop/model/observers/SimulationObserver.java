package agh.ics.oop.model.observers;

import agh.ics.oop.model.Animal;
import agh.ics.oop.model.WorldMap;
import agh.ics.oop.model.util.ChartData;

import java.util.ArrayList;

public interface SimulationObserver {

    ChartData getChartData();
    void updateStats(WorldMap map, int day);
    void registerDeath(Animal animal);

    // skala projektu jest zbyt mała, aby aktualizować statystyki dopiero po
    // konkretnym wydarzeniu lepiej jeden raz zawsze wszystkie
    // czytelność lepsza niż mikrooptymalizacja
}
