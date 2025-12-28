package agh.ics.oop.model.observers;

public interface SimulationChangeListener {
    void simulationStatisticsChanged(StatisticsTracker statistics);
    void simulationChartsChanged(StatisticsTracker statistics);

    // skala projektu jest zbyt mała, aby aktualizować statystyki dopiero po
    // konkretnym wydarzeniu lepiej jeden raz zawsze wszystkie
    // czytelność lepsza niż mikrooptymalizacja
}
