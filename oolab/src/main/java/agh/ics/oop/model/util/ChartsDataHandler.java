package agh.ics.oop.model.util;

import javafx.scene.chart.XYChart;

public class ChartsDataHandler {
    private final XYChart.Series<Number, Number> animalsPerDay;
    private final XYChart.Series<Number, Number> grassesPerDay;
    private final XYChart.Series<Number, Number> averageEnergyPerDay;
    private final XYChart.Series<Number, Number> averageLifeSpanPerDay;
    private final XYChart.Series<Number, Number> averageKidsCountPerDay;
    private int prev = -1;

    public ChartsDataHandler() {
        animalsPerDay = new XYChart.Series<>();
        grassesPerDay = new XYChart.Series<>();
        averageEnergyPerDay = new XYChart.Series<>();
        averageLifeSpanPerDay = new XYChart.Series<>();
        averageKidsCountPerDay = new XYChart.Series<>();
    }

    public void updateChart(ChartData chartData){
        if(animalsPerDay.getData().size() > 100){
            animalsPerDay.getData().removeFirst();
            grassesPerDay.getData().removeFirst();
            averageEnergyPerDay.getData().removeFirst();
            averageLifeSpanPerDay.getData().removeFirst();
            averageKidsCountPerDay.getData().removeFirst();
        }

        animalsPerDay.getData().add(new XYChart.Data<>(chartData.getDay(), chartData.getAnimals()));
        grassesPerDay.getData().add(new XYChart.Data<>(chartData.getDay(), chartData.getGrasses()));
        averageEnergyPerDay.getData().add(new XYChart.Data<>(chartData.getDay(), chartData.getAverageEnergy()));
        averageLifeSpanPerDay.getData().add(new XYChart.Data<>(chartData.getDay(), chartData.getAverageLifeSpan()));
        averageKidsCountPerDay.getData().add(new XYChart.Data<>(chartData.getDay(), chartData.getAverageKidsCount()));
        prev = chartData.getDay();
    }


    public XYChart.Series<Number, Number> getAnimalsPerDay() {
        return animalsPerDay;
    }

    public XYChart.Series<Number, Number> getGrassesPerDay() {
        return grassesPerDay;
    }

    public XYChart.Series<Number, Number> getAverageEnergyPerDay() {
        return averageEnergyPerDay;
    }

    public XYChart.Series<Number, Number> getAverageLifeSpanPerDay() {
        return averageLifeSpanPerDay;
    }

    public XYChart.Series<Number, Number> getAverageKidsCountPerDay() {
        return averageKidsCountPerDay;
    }
}
