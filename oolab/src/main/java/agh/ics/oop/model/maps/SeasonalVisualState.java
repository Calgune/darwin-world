package agh.ics.oop.model.maps;

import agh.ics.oop.MapVisualState;
import javafx.scene.paint.Color;

public class SeasonalVisualState implements MapVisualState {
    double coldFactor;
    double greenChangeSpeed = 1.8;
    double blueChangeSpeed = 0.4;

    public SeasonalVisualState(double factor){
        this.coldFactor = factor;
    }

    @Override
    public Color getSteppesColor() {
        if (coldFactor < 0.5)
            return Color.rgb(144, 238, 144, 1-coldFactor*greenChangeSpeed);
        return Color.color(0.5, 0.7, 1.0, coldFactor-blueChangeSpeed);
    }

    @Override
    public Color getJungleColor() {
        if (coldFactor < 0.5)
            return Color.rgb(34, 139, 34, 1-coldFactor*greenChangeSpeed);
        return Color.color(0.2, 0.4, 1.0, coldFactor-blueChangeSpeed);
    }
}
