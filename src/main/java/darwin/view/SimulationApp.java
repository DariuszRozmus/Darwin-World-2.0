package darwin.view;

import com.pixelduke.control.skin.FXSkins;
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

        Scene scene = new Scene(root); // <-- tworzysz scenę
        scene.getStylesheets().add(FXSkins.getStylesheetURL()); // <-- dodajesz FXSkins do sceny
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

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Symulacja");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



//    private void startSimulation(PreliminaryData data) {
//        try {
//            FXMLLoader loader = new FXMLLoader(
//                    getClass().getClassLoader().getResource("simulation.fxml")
//            );
//
//            Parent root = loader.load();
//            SimulationPresenter presenter = loader.getController();
//
//            presenter.startSimulation(data);
//
//            Stage stage = new Stage();
//            stage.setScene(new Scene(root));
//            stage.setTitle("Symulacja");
//            stage.show();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//    public void startSimulation(PreliminaryData data) {
//        WorldMap worldMap = new WorldMap(data);
//        Simulation simulation = new Simulation(worldMap, this, data);
//
//        Thread simThread = new Thread(simulation);
//        simThread.setDaemon(true);
//        simThread.start();
//    }
//
//    private void startSimulation(PreliminaryData data) throws IOException {
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("simulation.fxml"));
//        Parent root = loader.load();
//
//        SimulationPresenter presenter = loader.getController();
//        presenter.startSimulation(data);
//
//        Stage stage = new Stage();
//        stage.setScene(new Scene(root));
//        stage.setTitle("Simulation");
//        stage.show();
//    }


    private void configureConfigStage(Stage primaryStage, BorderPane viewRoot) {
        Scene scene = new Scene(viewRoot);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Darwin Simulation");

        primaryStage.minWidthProperty().bind(viewRoot.minWidthProperty());
        primaryStage.minHeightProperty().bind(viewRoot.minHeightProperty());

        primaryStage.show();
    }

    private void configureStage(Stage primaryStage, BorderPane viewRoot) {
        Scene scene = new Scene(viewRoot);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Darwin Simulation");

        primaryStage.minWidthProperty().bind(viewRoot.minWidthProperty());
        primaryStage.minHeightProperty().bind(viewRoot.minHeightProperty());

        primaryStage.show();
    }
}
