package agh.ics.oop.model;

import agh.ics.oop.MapVisualState;
import javafx.scene.paint.Color;

public class NormalVisualState implements MapVisualState {
    @Override
    public Color getSteppesColor() {
        return Color.rgb(144, 238, 144);
    }

    @Override
    public Color getJungleColor() {
        return Color.rgb(34, 139, 34);
    }
}
