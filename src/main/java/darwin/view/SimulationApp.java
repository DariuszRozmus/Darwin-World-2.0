<<<<<<< Updated upstream
package view;

=======
package darwin.view;

import darwin.presenter.SimulationPresenter;
>>>>>>> Stashed changes
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
<<<<<<< Updated upstream
import presenter.SimulationPresenter;
=======
>>>>>>> Stashed changes

public class SimulationApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
<<<<<<< Updated upstream
        primaryStage.show();
        FXMLLoader loader = new FXMLLoader(); // zainicjowanie wczytywania FXML

        // wczytanie zasobu z katalogu resources (uniwersalny sposób)
        loader.setLocation(getClass().getClassLoader().getResource("simulation.fxml"));

        // Wczytanie FXML, konwersja FXML -> obiekty w Javie
        BorderPane viewRoot = loader.load();
        SimulationPresenter presenter = loader.getController();
        configureStage(primaryStage,viewRoot);
=======

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("simulation.fxml"));

        BorderPane viewRoot = loader.load();
        SimulationPresenter presenter = loader.getController();
        configureStage(primaryStage, viewRoot);
>>>>>>> Stashed changes

        System.out.println("system wystartowal");
    }

    private void configureStage(Stage primaryStage, BorderPane viewRoot) {
<<<<<<< Updated upstream
        // stworzenie sceny (panelu do wyświetlania wraz zawartością z FXML)
        var scene = new Scene(viewRoot);

        // ustawienie sceny w oknie
        primaryStage.setScene(scene);

        // konfiguracja okna
        primaryStage.setTitle("darwin.Simulation app");
        primaryStage.minWidthProperty().bind(viewRoot.minWidthProperty());
        primaryStage.minHeightProperty().bind(viewRoot.minHeightProperty());
        // primaryStage.show();
=======
        Scene scene = new Scene(viewRoot);

        primaryStage.setScene(scene);
        primaryStage.setTitle("darwin.model.Simulation app");

        primaryStage.show();

        primaryStage.minWidthProperty().bind(viewRoot.minWidthProperty());
        primaryStage.minHeightProperty().bind(viewRoot.minHeightProperty());

        primaryStage.show();
>>>>>>> Stashed changes
    }
}
