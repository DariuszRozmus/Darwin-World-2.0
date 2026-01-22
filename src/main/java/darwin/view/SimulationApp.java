package darwin.view;

import darwin.config.ConfigController;
import darwin.config.PreliminaryData;
import darwin.model.Simulation;
import darwin.model.map.WorldMap;
import darwin.presenter.SimulationPresenter;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;

import java.io.IOException;

public class SimulationApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader configLoader = new FXMLLoader(
                getClass().getClassLoader().getResource("config.fxml")
        );
        BorderPane root = configLoader.load();

        ConfigController controller = configLoader.getController();
        controller.setSimulationStarter(this::startSimulation);

        JMetro jMetro = new JMetro(Style.LIGHT);
        var scene = new Scene(root);
        jMetro.setScene(scene);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Konfiguracja symulacji");
        primaryStage.show();
    }

    private void startSimulation(PreliminaryData data) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getClassLoader().getResource("simulation.fxml")
            );

            Parent root = loader.load();
            SimulationPresenter presenter = loader.getController();
            presenter.init(data);

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);

            JMetro jMetro = new JMetro(Style.LIGHT);
            jMetro.setScene(scene);

            stage.setTitle("Symulacja");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
