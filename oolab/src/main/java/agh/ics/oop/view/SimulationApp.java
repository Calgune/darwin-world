package agh.ics.oop.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class SimulationApp extends Application {
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(); // zainicjowanie wczytywania FXML

    // wczytanie zasobu z katalogu resources (uniwersalny sposób)
        loader.setLocation(getClass().getClassLoader().getResource("menu.fxml"));

    // Wczytanie FXML, konwersja FXML -> obiekty w Javie
        BorderPane menuRoot = loader.load();

        Scene scene = new Scene(menuRoot);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Panel parametrow");
        // za kazdym razem tworzymy nowy stage, nowa scene, viewroot tez moze byc nowy
        // jak zmienimy parametry

        primaryStage.show();

//        SimulationPresenter presenter = loader.getController();
//        configureStage(primaryStage, viewRoot);

//        WorldMap map = new GrassField(10);
//        presenter.setWorldMap(map);
//        map.addObserver(presenter);
    }

//    private void configureStage(Stage primaryStage, BorderPane viewRoot) {
//        // stworzenie sceny (panelu do wyświetlania wraz zawartością z FXML)
//        var scene = new Scene(viewRoot);
//
//        // ustawienie sceny w oknie
//        primaryStage.setScene(scene);
//
//        // konfiguracja okna
//        primaryStage.setTitle("Simulation app");
//        primaryStage.minWidthProperty().bind(viewRoot.minWidthProperty());
//        primaryStage.minHeightProperty().bind(viewRoot.minHeightProperty());
//    }
}
