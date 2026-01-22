package agh.ics.oop.presenter;

import agh.ics.oop.SeasonalSimulation;
import agh.ics.oop.NormalSimulation;
import agh.ics.oop.model.*;
import agh.ics.oop.model.maps.CylindricalMap;
import agh.ics.oop.model.observers.MapChangeListener;
import agh.ics.oop.model.observers.SimulationChangeListener;
import agh.ics.oop.model.observers.StatisticsTracker;
import agh.ics.oop.model.simulation.SimulationType;
import agh.ics.oop.model.simulation.Simulation;
import agh.ics.oop.model.simulation.SimulationEngine;
import agh.ics.oop.model.util.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

public class SimulationPresenter implements MapChangeListener, SimulationChangeListener {
    private final ChartsDataHandler chartsDataHandler = new ChartsDataHandler();
    private WorldMap map;
    private Simulation simulation;

    private int cellSize = 25;
    private Font font;
    private Animal trackedAnimal = null;
    private int rows;
    private int columns;
    private WriteToCSV csv;

    @FXML private ComboBox chartsComboBox;
    @FXML private Label dayLabel;
    @FXML public TextField minMutationLength;
    @FXML public TextField maxMutationLength;
    @FXML public CheckBox simulationTypeCheckButton;
    @FXML private LineChart<Number, Number> averageLifeSpanPerDayLineChart;
    @FXML private LineChart<Number, Number> averageEnergyPerDayLineChart;
    @FXML private LineChart<Number, Number> grassesPerDayLineChart;
    @FXML private LineChart<Number, Number> animalsPerDayLineChart;
    @FXML private LineChart<Number, Number> averageKidsCountPerDayLineChart;
    @FXML private TextField distanceToTransferHeatTextField;
    @FXML private TextField minimalTemperatureTextField;
    @FXML private TextField summerLengthTextField;
    @FXML private TextField winterLengthTextField;
    @FXML private Label temperatureLabel;
    @FXML private Label seasonLabel;
    @FXML private ComboBox<String> presetComboBox;
    @FXML private Label animalsOnMapLabel;
    @FXML private Label grassesOnMapLabel;
    @FXML private Label emptyCellsLabel;
    @FXML private Label mostPopularGenotypeLabel;
    @FXML private Label deadAnimalsAverageLifespanLabel;
    @FXML private Label animalsOnMapAverageEnergyLabel;
    @FXML private Label animalsOnMapAverageKidsCountLabel;
    @FXML private Canvas canvas;
    @FXML private TextField dailyEnergyDecay;
    @FXML private TextField breedingThreshold;
    @FXML private TextField breedingCost;
    @FXML private TextField fromGrassEnergy;
    @FXML private TextField mapWidthTextField;
    @FXML private TextField mapHeightTextField;
    @FXML private TextField refreshTimeTextField;
    @FXML private TextField animalsCntTextField;
    @FXML private TextField genomesTextField;
    @FXML private TextField initialEnergyTextField;
    @FXML private TextField dailyGrassesTextField;
    @FXML private TextField initialGrassesTextField;
    @FXML private Label animalMovementLabel;
    @FXML private Button startButton;
    @FXML private Button pauseButton;

    @FXML private VBox trackedAnimalBox;
    @FXML private Label individualGenomeLabel;
    @FXML private Label individualGenotypeLabel;
    @FXML private Label individualEnergyLabel;
    @FXML private Label individualDescendantsLabel;
    @FXML private Label individualAgeLabel;
    @FXML private Label individualPlantsEatenLabel;
    @FXML private Label individualKidsLabel;
    @FXML private Label individualDeathDayLabel;

    @FXML
    void selectChart(){
        String selected = chartsComboBox.getValue().toString();
        hideCharts();
        if (selected.equals("liczba zwierząt od dnia")) showAnimalsPerDayLineChart();
        else if (selected.equals("liczba traw od dnia")) showGrassesPerDayLineChart();
        else if (selected.equals("średnia liczba energia od dnia")) showAverageEnergyPerDayLineChart();
        else if (selected.equals("średnia długość życia")) showAverageLifeSpanPerDayLineChart();
        else if (selected.equals("średnia liczba dzieci")) showAverageKidsCount();
    }

    void initChartsComboBox(){
        chartsComboBox.getItems().clear();
        chartsComboBox.getItems().addAll("liczba zwierząt od dnia",
                "liczba traw od dnia",
                "średnia liczba energia od dnia",
                "średnia długość życia",
                "średnia liczba dzieci");
    }

    private void connectChartsDataWithCharts(){
        animalsPerDayLineChart.getData().add(chartsDataHandler.getAnimalsPerDay());
        grassesPerDayLineChart.getData().add(chartsDataHandler.getGrassesPerDay());
        averageEnergyPerDayLineChart.getData().add(chartsDataHandler.getAverageEnergyPerDay());
        averageLifeSpanPerDayLineChart.getData().add(chartsDataHandler.getAverageLifeSpanPerDay());
        averageKidsCountPerDayLineChart.getData().add(chartsDataHandler.getAverageKidsCountPerDay());
    }

    public void showAnimalsPerDayLineChart(){
        hideCharts();
        animalsPerDayLineChart.setManaged(true);
        animalsPerDayLineChart.setVisible(true);
    }

    public void showGrassesPerDayLineChart(){
        hideCharts();
        grassesPerDayLineChart.setManaged(true);
        grassesPerDayLineChart.setVisible(true);
    }

    public void showAverageEnergyPerDayLineChart(){
        hideCharts();
        averageEnergyPerDayLineChart.setManaged(true);
        averageEnergyPerDayLineChart.setVisible(true);
    }

    public void showAverageLifeSpanPerDayLineChart(){
        hideCharts();
        averageLifeSpanPerDayLineChart.setManaged(true);
        averageLifeSpanPerDayLineChart.setVisible(true);
    }

    public void showAverageKidsCount(){
        hideCharts();
        averageKidsCountPerDayLineChart.setManaged(true);
        averageKidsCountPerDayLineChart.setVisible(true);
    }

    private void hideCharts(){
        animalsPerDayLineChart.setManaged(false); animalsPerDayLineChart.setVisible(false);
        grassesPerDayLineChart.setManaged(false); grassesPerDayLineChart.setVisible(false);
        averageEnergyPerDayLineChart.setManaged(false); averageEnergyPerDayLineChart.setVisible(false);
        averageLifeSpanPerDayLineChart.setManaged(false); averageLifeSpanPerDayLineChart.setVisible(false);
        averageKidsCountPerDayLineChart.setManaged(false); averageKidsCountPerDayLineChart.setVisible(false);
    }

    public void setWorldMap(WorldMap map){
        this.map = map;
        map.addObserver(this);
    }

    public void setSimulation(Simulation simulation) {
        this.simulation = simulation;
    }

    @FXML
    private void pauseButtonClicked() {
        if (this.simulation != null) {
            this.simulation.startStop();
        }
    }

    @FXML
    private void openCSVClicked() {
        if (this.simulation != null) {
            WriteToCSV currCSV = this.simulation.getCSV();
            if (currCSV != null) {
                currCSV.openFile();
            } else {
                System.out.println("Plik nie został jeszcze utworzony");
            }
        }
    }

    @FXML
    private void selectPresets() {
        String selected = presetComboBox.getValue();
        if (selected == null) return;
        switch (selected) {
            case "Bezpłodni" -> {
                mapWidthTextField.setText("25"); mapHeightTextField.setText("12");
                animalsCntTextField.setText("15"); initialGrassesTextField.setText("15");
                initialEnergyTextField.setText("40"); dailyGrassesTextField.setText("2");
                dailyEnergyDecay.setText("5"); breedingThreshold.setText("200"); breedingCost.setText("200");
            }
            case "Indie" -> {
                mapWidthTextField.setText("25"); mapHeightTextField.setText("12");
                animalsCntTextField.setText("200"); initialGrassesTextField.setText("70");
                dailyGrassesTextField.setText("20"); initialEnergyTextField.setText("50");
                breedingCost.setText("3"); breedingThreshold.setText("3"); refreshTimeTextField.setText("10");
            }
            case "Postapo" -> {
                mapWidthTextField.setText("25"); mapHeightTextField.setText("12");
                animalsCntTextField.setText("6"); initialGrassesTextField.setText("10");
                dailyGrassesTextField.setText("1"); dailyEnergyDecay.setText("3");
                initialEnergyTextField.setText("30"); breedingCost.setText("10");
                breedingThreshold.setText("10"); refreshTimeTextField.setText("50");
            }
        }
    }

    @FXML
    public void initialize() {
        if (presetComboBox != null) {
            presetComboBox.getItems().clear();
            presetComboBox.getItems().addAll("Bezpłodni", "Indie", "Postapo");
        }
        if (canvas != null) {
            canvas.setOnMouseClicked(event -> {
                if (simulation != null && simulation.isPaused() && map != null) {
                    double mouseX = event.getX();
                    double mouseY = event.getY();
                    Boundary boundary = map.getBoundary();
                    int x = (int) (mouseX / cellSize) - 1;
                    int y = boundary.upperRight().getY() - ((int) (mouseY / cellSize) - 1);
                    Vector2d clickedPosition = new Vector2d(x, y);
                    Object object = map.objectAt(clickedPosition);
                    if (object instanceof Animal animal) {
                        this.trackedAnimal = animal;
                        if (trackedAnimalBox != null) {
                            trackedAnimalBox.setManaged(true);
                            trackedAnimalBox.setVisible(true);
                        }
                        updateTrackedAnimal();
                        drawMap(map);
                    }
                }
            });
        }
    }

    @FXML
    private void saveConfiguration() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Zapisz konfigurację");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Plik tesktowy", "*.txt"));
        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            try {
                SimulationType type = getSimulationType();
                boolean isSeasonal = (type == SimulationType.Seasonal);

                MapSettings settings = new MapSettings.Builder()
                        .boundary(new Boundary(new Vector2d(0, 0), new Vector2d(
                                Integer.parseInt(mapWidthTextField.getText()),
                                Integer.parseInt(mapHeightTextField.getText()))))
                        .initialAnimalCount(Integer.parseInt(animalsCntTextField.getText()))
                        .initialAnimalEnergy(Integer.parseInt(initialEnergyTextField.getText()))
                        .genomeLength(Integer.parseInt(genomesTextField.getText()))
                        .initialGrassCount(Integer.parseInt(initialGrassesTextField.getText()))
                        .newDailyGrasses(Integer.parseInt(dailyGrassesTextField.getText()))
                        .refreshSpeed(Integer.parseInt(refreshTimeTextField.getText()))
                        .energyFromGrass(Integer.parseInt(fromGrassEnergy.getText()))
                        .dailyEnergyDecay(Integer.parseInt(dailyEnergyDecay.getText()))
                        .reproductionEnergyCost(Integer.parseInt(breedingCost.getText()))
                        .reproductionEnergyThreshold(Integer.parseInt(breedingThreshold.getText()))
                        .summerLengthInDays(isSeasonal ? Integer.parseInt(summerLengthTextField.getText()) : 0)
                        .winterLengthInDays(isSeasonal ? Integer.parseInt(winterLengthTextField.getText()) : 0)
                        .minimalTemperatureInWinter(isSeasonal ? Integer.parseInt(minimalTemperatureTextField.getText()) : 0)
                        .distanceToTransferHeat(isSeasonal ? Integer.parseInt(distanceToTransferHeatTextField.getText()) : 0)
                        .build();
                ConfigurationHandler.saveConfiguration(file, settings);
            } catch (Exception e) { System.err.println("Błąd - złe dane"); }
        }
    }

    @FXML
    private void loadConfiguration() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Wczytaj konfigurację");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Pliki tekstowe (*.txt)", "*.txt"));
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            try {
                Properties config = ConfigurationHandler.loadConfiguration(file);
                mapWidthTextField.setText(config.getProperty("width", "25"));
                mapHeightTextField.setText(config.getProperty("height", "12"));
                animalsCntTextField.setText(config.getProperty("initialAnimals", "15"));
                initialEnergyTextField.setText(config.getProperty("initialEnergy", "100"));
                genomesTextField.setText(config.getProperty("genomeLength", "7"));
                initialGrassesTextField.setText(config.getProperty("initialGrass", "30"));
                dailyGrassesTextField.setText(config.getProperty("dailyGrass", "7"));
                refreshTimeTextField.setText(config.getProperty("refreshSpeed", "100"));
                fromGrassEnergy.setText(config.getProperty("energyFromGrass", "2"));
                dailyEnergyDecay.setText(config.getProperty("dailyEnergyDecay", "2"));
                breedingCost.setText(config.getProperty("reproductionCost", "10"));
                breedingThreshold.setText(config.getProperty("reproductionThreshold", "12"));
                summerLengthTextField.setText(config.getProperty("summerLengthInDays", "30"));
                winterLengthTextField.setText(config.getProperty("winterLengthInDays", "30"));
                minimalTemperatureTextField.setText(config.getProperty("minimalTemperatureInWinter", "-20"));
                distanceToTransferHeatTextField.setText(config.getProperty("distanceToTransferHeat", "2"));
            } catch (IOException e) { System.err.println("Błąd wczytywania pliku"); }
        }
    }

    private void drawTextInTheMiddleOfRectangle(GraphicsContext gc, String string, int x, int y){
        Text text = new Text(string); text.setFont(font);
        double tw = text.getLayoutBounds().getWidth();
        double th = text.getLayoutBounds().getHeight();
        gc.fillText(string, x + (cellSize - tw) / 2, y + (cellSize - th) / 2 + th * 0.7);
    }

    private void drawAxis(GraphicsContext gc, int start, int end, boolean horizontal){
        int k = 1; int step = start <= end ? 1 : -1;
        for (int i = start; i != end + step; i += step){
            int x = horizontal ? k * cellSize : 0;
            int y = horizontal ? 0 : k * cellSize;
            drawTextInTheMiddleOfRectangle(gc, String.valueOf(i), x, y);
            k++;
        }
    }

    private void drawRectangle(GraphicsContext gc, int v, int v1, int v2, int v3, Color color){
        gc.save(); gc.setFill(color); gc.fillRect(v, v1, v2, v3); gc.restore();
    }

    private void drawHealthBar(GraphicsContext gc, int x, int y, int energy){
        int marginFromSides = 3;
        int marginFromTop = 1;
        drawRectangle(gc, x+marginFromSides, y+marginFromTop, cellSize -marginFromSides*2,2, Color.RED);
        int blueLength = (int) Utility.mapValue(energy, 0, map.getMapSettings().getReproductionEnergyThreshold(), 0, cellSize -marginFromSides*2);
        drawRectangle(gc, x+marginFromSides, y+marginFromTop, blueLength,2, Color.BLUE);
    }

    private void initCanvas(WorldMap worldMap){
        rows = worldMap.getRowsCount();
        columns = worldMap.getColumnsCount();

        cellSize = (int) Math.min(canvas.getWidth() / columns, canvas.getHeight() / rows);

        canvas.setHeight(cellSize * rows);
        canvas.setWidth(cellSize * columns);

        font = new Font(Math.floor(cellSize * 0.7));
    }

    public void drawMap(WorldMap worldMap){
        Boundary boundary = worldMap.getBoundary();
        GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.setStroke(Color.valueOf("#3d3d3d")); gc.setLineWidth(0.2); gc.setFont(font);
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        drawTextInTheMiddleOfRectangle(gc, "x/y", 0, 0);

        // rysowanie elementów mapy (zwierzki i trawa w tym przypadku)
        // x i y w pętli tłumaczy wspołrzędne mapy na wspołrzędne canvasu
        for(int i = boundary.upperRight().getY(); i >= boundary.lowerLeft().getY(); i--){
            for (int j = boundary.lowerLeft().getX(); j <= boundary.upperRight().getX(); j++){
                int x = (j - boundary.lowerLeft().getX() + 1) * cellSize;
                int y = (boundary.upperRight().getY() - i + 1) * cellSize;
                Vector2d position = new Vector2d(j, i);
                if(position.precedes(worldMap.getJungleBoundary().upperRight()) && position.follows(worldMap.getJungleBoundary().lowerLeft())){
                    drawRectangle(gc, x, y, cellSize, cellSize, simulation.getMapVisualState().getJungleColor());
                } else {
                    drawRectangle(gc, x, y, cellSize, cellSize, simulation.getMapVisualState().getSteppesColor());
                }
                if (worldMap.isOccupied(position)) {
                    Object object = worldMap.objectAt(position);
                    if (object instanceof Animal animal) {
                        gc.setFill(animal.getColor(simulation.getStatistics().getMostPopularGenotype()));
                        drawTextInTheMiddleOfRectangle(gc, animal.toString(), x, y);
                        drawHealthBar(gc, x, y, animal.getEnergy());
                        if (animal.equals(trackedAnimal)) {
                            gc.setFill(Color.rgb(255,0,0));
                            drawTextInTheMiddleOfRectangle(gc, animal.toString(), x, y);
                        }
                    } else {
                        gc.setFill(Color.rgb(0,0,0));
                        drawTextInTheMiddleOfRectangle(gc, worldMap.objectAt(position).toString(), x, y);
                    }
                }
            }
        }
        for (int i = 0; i <= columns + 1; i++) { gc.strokeLine(i * cellSize, 0, i * cellSize, canvas.getHeight());}
        for (int j = 0; j <= rows + 1; j++) {gc.strokeLine(0, j * cellSize, canvas.getWidth(), j * cellSize);}

        gc.setFill(Color.rgb(0,0,0));

        drawAxis(gc, boundary.lowerLeft().getX(), boundary.upperRight().getX(), true);
        drawAxis(gc, boundary.upperRight().getY()-1, boundary.lowerLeft().getY(), false);

    }

    private void updateChartsRange(){
        updateRange((NumberAxis) animalsPerDayLineChart.getXAxis(), chartsDataHandler.getAnimalsPerDay());
        updateRange((NumberAxis) grassesPerDayLineChart.getXAxis(), chartsDataHandler.getGrassesPerDay());
        updateRange((NumberAxis) averageEnergyPerDayLineChart.getXAxis(), chartsDataHandler.getAverageEnergyPerDay());
        updateRange((NumberAxis) averageKidsCountPerDayLineChart.getXAxis(), chartsDataHandler.getAverageKidsCountPerDay());
        updateRange((NumberAxis) averageLifeSpanPerDayLineChart.getXAxis(), chartsDataHandler.getAverageLifeSpanPerDay());
    }

    private void updateRange(NumberAxis axis, javafx.scene.chart.XYChart.Series<Number, Number> series) {
        if (!series.getData().isEmpty()) {
            axis.setLowerBound(series.getData().getFirst().getXValue().intValue());
            axis.setUpperBound(series.getData().getLast().getXValue().intValue());
        }
    }

    private void updateStatistics(StatisticsTracker statistics){
        animalsOnMapLabel.setText("liczba zwierzaków: %d".formatted(statistics.getAnimalsOnMap()));
        grassesOnMapLabel.setText("liczba traw: %d".formatted(statistics.getGrassesOnMap()));
        emptyCellsLabel.setText("liczba pustych komórek: %d".formatted(statistics.getEmptyCells()));
        mostPopularGenotypeLabel.setText("najpopulajniejszy genotyp: %d".formatted(statistics.getMostPopularGenotype()));
        deadAnimalsAverageLifespanLabel.setText("śr. dł. życia martwych zwierzaków: %.2f".formatted(statistics.getDeadAnimalsAverageLifespan()));
        animalsOnMapAverageEnergyLabel.setText("śr. energia zwierzaków: %.2f".formatted(statistics.getAnimalsOnMapAverageEnergy()));
        animalsOnMapAverageKidsCountLabel.setText("śr. liczba dzieci zwierzaków: %.2f".formatted(statistics.getAnimalsOnMapAverageKidsCount()));
        dayLabel.setText("dzień: %d".formatted(simulation.getDay()));
        if (simulation instanceof SeasonalSimulation){
            temperatureLabel.setText("temperatura: %.2f °C".formatted(((SeasonalSimulation) simulation).getTemperature()));
            seasonLabel.setText("pora roku: %s".formatted(((SeasonalSimulation) simulation).getSeason()));
        }
    }

    private void updateTrackedAnimal() {
        if (trackedAnimal == null) return;
        individualGenomeLabel.setText("Genom: " + trackedAnimal.getGenome().getGenes().toString());
        individualGenotypeLabel.setText("Genotyp: " + trackedAnimal.getGenome().getGenotype());
        individualEnergyLabel.setText("Energia: " + trackedAnimal.getEnergy());
        individualDescendantsLabel.setText("Potomkowie: " + trackedAnimal.getDescendantsCount());
        individualAgeLabel.setText("Wiek: " + trackedAnimal.getAge());
        individualPlantsEatenLabel.setText("Zjedzone rośliny: " + trackedAnimal.getPlantsEaten());
        individualKidsLabel.setText("Liczba dzieci: " + trackedAnimal.getKidsCount());
        individualDeathDayLabel.setText(trackedAnimal.getDeathDay() != null ? "Zmarło w dniu: " + trackedAnimal.getDeathDay() : "Zwierze żyje");
    }

    @Override
    public void simulationChartsChanged(StatisticsTracker statistics) {
        Platform.runLater(() -> {
            chartsDataHandler.updateChart(statistics.getChartData());
            updateChartsRange();
        });
    }

    @Override
    public void simulationStatisticsChanged(StatisticsTracker statistics) {
        Platform.runLater(() -> {
            updateStatistics(statistics);
            if (trackedAnimal != null) updateTrackedAnimal();
        });
    }

    @Override
    public void mapChanged(WorldMap worldMap, String message) {
        Platform.runLater(() -> {
            animalMovementLabel.setText(message);
            drawMap(worldMap);
        });
    }

    public void enableSeasonalLabels(){
        temperatureLabel.setManaged(true); temperatureLabel.setVisible(true);
        seasonLabel.setManaged(true); seasonLabel.setVisible(true);
    }

    public void simulationTypeCheckButtonClicked(){
        boolean disable = !simulationTypeCheckButton.isSelected();
        minimalTemperatureTextField.setDisable(disable); summerLengthTextField.setDisable(disable);
        winterLengthTextField.setDisable(disable); distanceToTransferHeatTextField.setDisable(disable);
    }

    public SimulationType getSimulationType(){ return simulationTypeCheckButton.isSelected() ? SimulationType.Seasonal : SimulationType.Normal; }

    private int boundaryGuard(int value, int min, int max) { return Math.max(min, Math.min(max, value)); }

    private int parseInteger(String text, int min, int max) {
        try {
            int value = Integer.parseInt(text);
            if (value < min || value > max) {
                throw new NumberFormatException("Out of range");
            }
            return value;
        } catch (NumberFormatException e) {
            errorPopup();
            throw new RuntimeException();
        }
    }

    private void errorPopup() {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
        alert.setTitle("Błąd");
        alert.setHeaderText("Błędne wartości");
        alert.setContentText("Wpisuj liczby całkowite w sugerowanych zakresach.");
        alert.showAndWait();
    }

    public void onSimulationStartClicked(ActionEvent actionEvent) throws Exception {
        try {
            int mapWidth = parseInteger(mapWidthTextField.getText(), 2, 50);
            int mapHeight = parseInteger(mapHeightTextField.getText(), 2, 50);
            int maxFields = (mapWidth + 1) * (mapHeight + 1);
            int initialGrasses = parseInteger(initialGrassesTextField.getText(), 0, maxFields);
            int dailyGrasses = parseInteger(dailyGrassesTextField.getText(), 0, maxFields / 2);
            int animalsCnt = parseInteger(animalsCntTextField.getText(), 1, maxFields * 2);
            int initialEnergy = parseInteger(initialEnergyTextField.getText(), 1, 1000);
            int genomesLength = parseInteger(genomesTextField.getText(), 1, 100);
            int energyDecay = parseInteger(dailyEnergyDecay.getText(), 0, 100);
            int reproductionThreshold = parseInteger(breedingThreshold.getText(), 1, 1000);
            int reproductionCost = parseInteger(breedingCost.getText(), 0, 500);
            int grassEnergy = parseInteger(fromGrassEnergy.getText(), 0, 500);
            int refreshTime = parseInteger(refreshTimeTextField.getText(), 10, 1000);
            int minMutation = parseInteger(minMutationLength.getText(), 0, genomesLength);
            int maxMutation = parseInteger(maxMutationLength.getText(), minMutation, genomesLength);

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("simulation.fxml"));
        BorderPane viewRoot = loader.load();
        SimulationPresenter presenter = loader.getController();

        MapSettings settings = new MapSettings.Builder()
                .boundary(new Boundary(new Vector2d(0,0), new Vector2d(mapWidth, mapHeight)))
                .initialGrassCount(initialGrasses).initialAnimalEnergy(initialEnergy).genomeLength(genomesLength)
                .initialAnimalCount(animalsCnt).newDailyGrasses(dailyGrasses).dailyEnergyDecay(energyDecay)
                .energyFromGrass(grassEnergy).reproductionEnergyCost(reproductionCost).reproductionEnergyThreshold(reproductionThreshold)
                .refreshSpeed(refreshTime).simulationType(getSimulationType())
                .minimalTemperatureInWinter(parseInteger(minimalTemperatureTextField.getText(), -50, 50))
                .distanceToTransferHeat(parseInteger(distanceToTransferHeatTextField.getText(), 0, 10))
                .winterLengthInDays(parseInteger(winterLengthTextField.getText(), 1, 100))
                .summerLengthInDays(parseInteger(summerLengthTextField.getText(), 1, 100))
                .minMutationLength(minMutation).maxMutationLength(maxMutation).build();

        WorldMap newMap = new CylindricalMap(settings);
        presenter.setWorldMap(newMap);
        Simulation simulation = (settings.getSimulationType() == SimulationType.Normal)
                ? new NormalSimulation(newMap, settings) : new SeasonalSimulation(newMap, settings);

        StatisticsTracker tracker = new StatisticsTracker();
        tracker.updateStats(newMap, 0);

        presenter.initChartsComboBox();
        presenter.initCanvas(presenter.map);

        presenter.setSimulation(simulation);
        SimulationEngine simulationEngine = new SimulationEngine(List.of(simulation));
        simulationEngine.runAsync();
        simulation.addObserver(presenter);

        Stage stage = new Stage();
        configureStage(stage, viewRoot);
        stage.setOnCloseRequest(event -> simulation.killSimulation());
        stage.show();

        presenter.connectChartsDataWithCharts();
        if(settings.getSimulationType() == SimulationType.Seasonal) presenter.enableSeasonalLabels();

        } catch (RuntimeException e) {
            System.out.println("Przerwano symulację, błędne dane");
        }
    }

    private void configureStage(Stage primaryStage, BorderPane viewRoot) {
        var scene = new Scene(viewRoot);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Simulation app");
        primaryStage.minWidthProperty().bind(viewRoot.minWidthProperty());
        primaryStage.minHeightProperty().bind(viewRoot.minHeightProperty());
    }
}