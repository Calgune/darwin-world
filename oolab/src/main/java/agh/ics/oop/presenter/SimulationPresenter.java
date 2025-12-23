package agh.ics.oop.presenter;

import agh.ics.oop.Simulation;
import agh.ics.oop.model.*;
import agh.ics.oop.model.observers.MapChangeListener;
import agh.ics.oop.model.observers.StatisticsTracker;
import agh.ics.oop.model.util.MapSettings;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.List;

public class SimulationPresenter implements MapChangeListener {
    private StatisticsTracker statisticsTracker;
    private final static int CELL_SIZE = 25;
    private final static Font FONT = new Font(14);

    @FXML
    public Label animalsOnMapLabel;

    @FXML
    public Label grassesOnMapLabel;

    @FXML
    public Label emptyCellsLabel;

    @FXML
    public Label mostPopularGenotypeLabel;

    @FXML
    public Label deadAnimalsAverageLifespanLabel;

    @FXML
    public Label animalsOnMapAverageEnergyLabel;

    @FXML
    public Label animalsOnMapAverageKidsCountLabel;

    @FXML
    public Canvas canvas;

    @FXML
    private TextField animalsCntTextField;

    @FXML
    private TextField genomesTextField;

    @FXML
    private TextField initialEnergyTextField;

    @FXML
    private TextField dailyGrassesTextField;

    @FXML
    private TextField initialGrassesTextField;

    @FXML
    private Label animalMovementLabel;

    @FXML
    private Button startButton;

    public void setWorldMap(WorldMap map){
        map.addObserver(this);
    }

    public void setStatisticsTracker(StatisticsTracker statisticsTracker) {
        this.statisticsTracker = statisticsTracker;
    }

    private void drawTextInTheMiddleOfRectangle(GraphicsContext graphicsContext2d, String string, int x, int y){
        Text text = new Text(string);
        text.setFont(FONT);

        double textWidth = text.getLayoutBounds().getWidth();
        double textHeight = text.getLayoutBounds().getHeight();

        double textX = x + (CELL_SIZE - textWidth) / 2;
        double textY = y + (CELL_SIZE - textHeight) / 2 + textHeight * 0.7;

        graphicsContext2d.fillText(string, textX, textY);
    }

    private void drawAxis(GraphicsContext graphicsContext, int start, int end, boolean horizontal){
        int k = 1;
        int step = start <= end ? 1 : -1;

        for (int i = start; i != end + step; i += step){
            int x = horizontal ? k * CELL_SIZE : 0;
            int y = horizontal ? 0 : k * CELL_SIZE;

            drawTextInTheMiddleOfRectangle(graphicsContext, String.valueOf(i), x, y);
            k++;
        }
    }

    private void drawRectangle(GraphicsContext graphicsContext, int v, int v1, int v2, int v3, Color color){
        graphicsContext.save();
        graphicsContext.setFill(color);

        graphicsContext.fillRect(v, v1, v2, v3);

        graphicsContext.restore();
    }

    public void drawMap(WorldMap worldMap){

        int rows = worldMap.getRowsCount();
        int columns = worldMap.getColumnsCount();

        Boundary boundary = worldMap.getBoundary();

        canvas.setHeight(CELL_SIZE*(columns+1));
        canvas.setWidth(CELL_SIZE*(rows+1));

        GraphicsContext graphicsContext2d = canvas.getGraphicsContext2D();
        graphicsContext2d.setStroke(Color.valueOf("#3d3d3d"));
        graphicsContext2d.setLineWidth(0.2);
        graphicsContext2d.setFont(FONT);

        graphicsContext2d.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        drawTextInTheMiddleOfRectangle(graphicsContext2d, "x/y", 0, 0);



        // rysowanie elementów mapy (zwierzki i trawa w tym przypadku)
        // x i y w pętli tłumaczy wspołrzędne mapy na wspołrzędne canvasu
        for(int i = boundary.upperRight().getY(); i >= boundary.lowerLeft().getY(); i--){
            for (int j = boundary.lowerLeft().getX(); j <= boundary.upperRight().getX(); j++){
                // dodajemy 1 do obu wspołrzędnych ponieważ, ponieważ pierwszy
                // wiersz i pierwsza kolmumna są przeznaczone na opis wspołrzędnych

                int x = (j - boundary.lowerLeft().getX() + 1) * CELL_SIZE;
                int y = (boundary.upperRight().getY() - i + 1) * CELL_SIZE;

                Vector2d position = new Vector2d(j, i);
                if(position.precedes(worldMap.getJungleBoundary().upperRight()) &&
                        position.follows(worldMap.getJungleBoundary().lowerLeft())){
                    drawRectangle(graphicsContext2d, x, y, CELL_SIZE, CELL_SIZE, Color.rgb(34, 139, 34));
                }
                else {
                    drawRectangle(graphicsContext2d, x, y, CELL_SIZE, CELL_SIZE, Color.rgb(144, 238, 144));
                }

                if(worldMap.isOccupied(position)){
                    drawTextInTheMiddleOfRectangle(graphicsContext2d, worldMap.objectAt(position).toString(), x, y);
                }
            }
        }

        // rysowanie siatki liniami
        for (int i = 0; i <= columns+1; i++) {
            int y = i * CELL_SIZE;
            graphicsContext2d.strokeLine(0, y, (rows+1) * CELL_SIZE, y);
        }

        for (int j = 0; j <= rows+1; j++) {
            int x = j * CELL_SIZE;
            graphicsContext2d.strokeLine(x, 0, x, (columns+1) * CELL_SIZE);
        }

        // rysowanie opisu osi x
        drawAxis(graphicsContext2d, boundary.lowerLeft().getX(), boundary.upperRight().getX(), true);

        // rysowanie opisu osi y
        drawAxis(graphicsContext2d, boundary.upperRight().getY(), boundary.lowerLeft().getY(), false);
    }

    private void updateStatistics(){
        animalsOnMapLabel.setText("liczba zwierzaków: %d".formatted(statisticsTracker.getAnimalsOnMap()));
        grassesOnMapLabel.setText("liczba traw: %d".formatted(statisticsTracker.getGrassesOnMap()));
        emptyCellsLabel.setText("liczba pustych komórek: %d".formatted(statisticsTracker.getEmptyCells()));
        mostPopularGenotypeLabel.setText("najpopulajniejszy genotyp: %d".formatted(statisticsTracker.getMostPopularGenotype()));
        deadAnimalsAverageLifespanLabel.setText("śr. dł. życia martwych zwierzaków: %.2f".formatted(statisticsTracker.getDeadAnimalsAverageLifespan()));
        animalsOnMapAverageEnergyLabel.setText("śr. energia zwierzaków: %.2f".formatted(statisticsTracker.getAnimalsOnMapAverageEnergy()));
        animalsOnMapAverageKidsCountLabel.setText("śr. liczba dzieci zwierzaków: %.2f".formatted(statisticsTracker.getAnimalsOnMapAverageKidsCount()));
    }

    @Override
    public void mapChanged(WorldMap worldMap, String message) {
        Platform.runLater(() -> {
            animalMovementLabel.setText(message);
            drawMap(worldMap);
            updateStatistics();
        });
    }

    public void onSimulationStartClicked(ActionEvent actionEvent) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("simulation.fxml"));
        BorderPane viewRoot = loader.load();

        SimulationPresenter presenter = loader.getController();

        MapSettings settings = new MapSettings.Builder()
                .boundary(new Boundary(new Vector2d(0,0), new Vector2d(25, 12)))
                .initialGrassCount(Integer.parseInt(initialGrassesTextField.getText()))
                .initialAnimalEnergy(Integer.parseInt(initialEnergyTextField.getText()))
                .genomeLength(Integer.parseInt(genomesTextField.getText()))
                .initialAnimalCount(Integer.parseInt(animalsCntTextField.getText()))
                .newDailyGrasses(Integer.parseInt(dailyGrassesTextField.getText()))
                .build();

        // jak stworzymy nowa mape to mozemy miec kilka okien.
        // Nie ma juz threadow i disablowania przycisku,
        // po usunieciu tego zaczelo dzialac wielokrotne otwieranie
        WorldMap newMap = new CylindricalMap(settings);
        presenter.setWorldMap(newMap);

        Simulation simulation = new Simulation(newMap, settings);
        StatisticsTracker tracker = new StatisticsTracker();
        tracker.updateStats(newMap);

        simulation.addObserver(tracker);
        presenter.setStatisticsTracker(tracker);

        SimulationEngine simulationEngine = new SimulationEngine(List.of(simulation));
        simulationEngine.runAsync();

        Stage stage = new Stage();
        configureStage(stage, viewRoot);
        stage.show();
    }

    private void configureStage(Stage primaryStage, BorderPane viewRoot) {
        // stworzenie sceny (panelu do wyświetlania wraz zawartością z FXML)
        var scene = new Scene(viewRoot);

        // ustawienie sceny w oknie
        primaryStage.setScene(scene);

        // konfiguracja okna
        primaryStage.setTitle("Simulation app");
        primaryStage.minWidthProperty().bind(viewRoot.minWidthProperty());
        primaryStage.minHeightProperty().bind(viewRoot.minHeightProperty());
    }
}