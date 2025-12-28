package agh.ics.oop.presenter;

import agh.ics.oop.SeasonalSimulation;
import agh.ics.oop.NormalSimulation;
import agh.ics.oop.model.*;
import agh.ics.oop.model.observers.MapChangeListener;
import agh.ics.oop.model.observers.StatisticsTracker;
import agh.ics.oop.model.util.ConfigurationHandler;
import agh.ics.oop.model.util.MapSettings;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

public class SimulationPresenter implements MapChangeListener {
    public Label dayLabel;
    private StatisticsTracker statisticsTracker;
    private WorldMap map;
    private Simulation simulation;
    private final static int CELL_SIZE = 25;
    private final static Font FONT = new Font(14);

    @FXML
    public TextField distanceToTransferHeatTextField;

    @FXML
    public CheckBox simulationTypeCheckButton;

    @FXML
    public TextField minimalTemperatureTextField;

    @FXML
    public TextField summerLengthTextField;

    @FXML
    public TextField winterLengthTextField;

    @FXML
    public Label temperatureLabel;

    @FXML
    public Label seasonLabel;

    @FXML
    private ComboBox<String> presetComboBox;

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
    private TextField dailyEnergyDecay;

    @FXML
    private TextField breedingThreshold;

    @FXML
    private TextField breedingCost;

    @FXML
    private TextField fromGrassEnergy;

    @FXML
    private TextField mapWidthTextField;

    @FXML
    private TextField mapHeightTextField;

    @FXML
    private TextField refreshTimeTextField;

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
    private void selectPresets() {
        String selected = presetComboBox.getValue();
        if (selected == null) return;

        switch (selected) {
            case "Bezpłodni" -> {
                mapWidthTextField.setText("25");
                mapHeightTextField.setText("12");
                animalsCntTextField.setText("15");
                initialGrassesTextField.setText("15");
                initialEnergyTextField.setText("40");
                dailyGrassesTextField.setText("2");
                dailyEnergyDecay.setText("5");
                breedingThreshold.setText("200");
                breedingCost.setText("200");
            }
            case "Indie" -> {
                mapWidthTextField.setText("25");
                mapHeightTextField.setText("12");
                animalsCntTextField.setText("200");
                initialGrassesTextField.setText("70");
                dailyGrassesTextField.setText("20");
                initialEnergyTextField.setText("50");
                breedingCost.setText("3");
                breedingThreshold.setText("3");
                refreshTimeTextField.setText("10");
            }
            case "Postapo" -> {
                mapWidthTextField.setText("25");
                mapHeightTextField.setText("12");
                animalsCntTextField.setText("6");
                initialGrassesTextField.setText("10");
                dailyGrassesTextField.setText("1");
                dailyEnergyDecay.setText("3");
                initialEnergyTextField.setText("30");
                breedingCost.setText("10");
                breedingThreshold.setText("10");
                refreshTimeTextField.setText("50");
            }
        }
    }

    @FXML
    public void initialize() {
        if (presetComboBox != null) {
            presetComboBox.getItems().clear();
            presetComboBox.getItems().addAll("Bezpłodni", "Indie", "Postapo");
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
                        .build();

                ConfigurationHandler.saveConfiguration(file, settings);
            } catch (Exception e) {
                System.err.println("Błąd - złe dane");
            }
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

            } catch (IOException e) {
                System.err.println("Błąd wczytywania pliku");
            }
        }
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
                    drawRectangle(graphicsContext2d, x, y, CELL_SIZE, CELL_SIZE, simulation.getMapVisualState().getJungleColor());
                }
                else {
                    drawRectangle(graphicsContext2d, x, y, CELL_SIZE, CELL_SIZE, simulation.getMapVisualState().getSteppesColor());
                }

                if (worldMap.isOccupied(position)) {
                    Object object = worldMap.objectAt(position);
                    if (object instanceof Animal animal) {
                        graphicsContext2d.setFill(animal.getColor(statisticsTracker.getMostPopularGenotype()));
                        drawTextInTheMiddleOfRectangle(graphicsContext2d, animal.toString(), x, y);
                    } else {
                        graphicsContext2d.setFill(Color.rgb(0,0,0));
                        drawTextInTheMiddleOfRectangle(graphicsContext2d, worldMap.objectAt(position).toString(), x, y);
                    }
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
        dayLabel.setText("dzień: %d".formatted(simulation.getDay()));

        if (simulation instanceof SeasonalSimulation){
            temperatureLabel.setText("temperatura: %.2f °C".formatted(((SeasonalSimulation) simulation).getTemperature()));
            seasonLabel.setText("pora roku: %s".formatted(((SeasonalSimulation) simulation).getSeason()));
        }
    }

    @Override
    public void mapChanged(WorldMap worldMap, String message) {
        Platform.runLater(() -> {
            animalMovementLabel.setText(message);
            drawMap(worldMap);
            updateStatistics();
        });
    }

    public void enableSeasonalLabels(){
        temperatureLabel.setManaged(true);
        temperatureLabel.setVisible(true);
        seasonLabel.setManaged(true);
        seasonLabel.setVisible(true);
    }

    public void simulationTypeCheckButtonClicked(){
        boolean disable = !simulationTypeCheckButton.isSelected();
        minimalTemperatureTextField.setDisable(disable);
        summerLengthTextField.setDisable(disable);
        winterLengthTextField.setDisable(disable);
        distanceToTransferHeatTextField.setDisable(disable);
    }

    public SimulationType getSimulationType(){
        if (simulationTypeCheckButton.isSelected())
            return SimulationType.Seasonal;
        return SimulationType.Normal;
    }

    public void onSimulationStartClicked(ActionEvent actionEvent) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("simulation.fxml"));
        BorderPane viewRoot = loader.load();

        SimulationPresenter presenter = loader.getController();

        int mapWidth = Integer.parseInt(mapWidthTextField.getText());
        int mapHeight = Integer.parseInt(mapHeightTextField.getText());
        int maxFields = (mapWidth + 1) * (mapHeight + 1);
        int initialGrasses = Integer.parseInt(initialGrassesTextField.getText());
        int initialEnergy = Integer.parseInt(initialEnergyTextField.getText());
        int genomesLength = Integer.parseInt(genomesTextField.getText());
        int animalsCnt = Integer.parseInt(animalsCntTextField.getText());
        int dailyGrasses = Integer.parseInt(dailyGrassesTextField.getText());
        int energyDecay = Integer.parseInt(dailyEnergyDecay.getText());
        int reproductionThreshold = Integer.parseInt(breedingThreshold.getText());
        int reproductionCost = Integer.parseInt(breedingCost.getText());
        int grassEnergy = Integer.parseInt(fromGrassEnergy.getText());
        int refreshTime = Integer.parseInt(refreshTimeTextField.getText());

        MapSettings settings = new MapSettings.Builder()
                .boundary(new Boundary(new Vector2d(0,0), new Vector2d(mapWidth, mapHeight)))
                .initialGrassCount(initialGrasses)
                .initialAnimalEnergy(initialEnergy)
                .genomeLength(genomesLength)
                .initialAnimalCount(animalsCnt)
                .newDailyGrasses(dailyGrasses)
                .dailyEnergyDecay(energyDecay)
                .energyFromGrass(grassEnergy)
                .reproductionEnergyCost(reproductionCost)
                .reproductionEnergyThreshold(reproductionThreshold)
                .refreshSpeed(refreshTime)
                .simulationType(getSimulationType())
                .minimalTemperatureInWinter(Integer.parseInt(minimalTemperatureTextField.getText()))
                .distanceToTransferHeat(Integer.parseInt(distanceToTransferHeatTextField.getText()))
                .winterLengthInDays(Integer.parseInt(winterLengthTextField.getText()))
                .summerLengthInDays(Integer.parseInt(summerLengthTextField.getText()))
                .build();

        // jak stworzymy nowa mape to mozemy miec kilka okien.
        // Nie ma juz threadow i disablowania przycisku,
        // po usunieciu tego zaczelo dzialac wielokrotne otwieranie
        WorldMap newMap = new CylindricalMap(settings);
        presenter.setWorldMap(newMap);

        Simulation simulation;
        if(settings.getSimulationType() == SimulationType.Normal)
            simulation = new NormalSimulation(newMap, settings);
        else{
            simulation = new SeasonalSimulation(newMap, settings);
        }

        StatisticsTracker tracker = new StatisticsTracker();
        tracker.updateStats(newMap);

        simulation.addObserver(tracker);
        presenter.setStatisticsTracker(tracker);

        presenter.setSimulation(simulation);
        SimulationEngine simulationEngine = new SimulationEngine(List.of(simulation));
        simulationEngine.runAsync();

        Stage stage = new Stage();
        configureStage(stage, viewRoot);

        stage.setOnCloseRequest(event -> {
            simulation.killSimulation();
        });
        stage.show();

        if(settings.getSimulationType() == SimulationType.Seasonal)
            presenter.enableSeasonalLabels();
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