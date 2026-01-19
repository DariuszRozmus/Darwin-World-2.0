package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import presenter.SimulationPresenter;

public class SimulationApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.show();
        FXMLLoader loader = new FXMLLoader(); // zainicjowanie wczytywania FXML

        // wczytanie zasobu z katalogu resources (uniwersalny sposób)
        loader.setLocation(getClass().getClassLoader().getResource("simulation.fxml"));

        // Wczytanie FXML, konwersja FXML -> obiekty w Javie
        BorderPane viewRoot = loader.load();
        SimulationPresenter presenter = loader.getController();
        configureStage(primaryStage,viewRoot);

        System.out.println("system wystartowal");
    }

    private void configureStage(Stage primaryStage, BorderPane viewRoot) {
        // stworzenie sceny (panelu do wyświetlania wraz zawartością z FXML)
        var scene = new Scene(viewRoot);

        // ustawienie sceny w oknie
        primaryStage.setScene(scene);

        // konfiguracja okna
        primaryStage.setTitle("darwin.Simulation app");
        primaryStage.minWidthProperty().bind(viewRoot.minWidthProperty());
        primaryStage.minHeightProperty().bind(viewRoot.minHeightProperty());
        // primaryStage.show();
    }
}
