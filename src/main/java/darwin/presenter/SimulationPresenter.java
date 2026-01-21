package darwin.presenter;

import darwin.model.Simulation;
import darwin.config.PreliminaryData;
import darwin.model.Vector2d;
import darwin.model.elements.WorldElement;
import darwin.model.map.WorldMap;
//import darwin.view.SimulationController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SimulationPresenter {

    @FXML
    private Canvas mapGrid;

    @FXML
    private Button startButton;

    @FXML
    private Button stopButton;

    public void init(PreliminaryData data) {
        WorldMap worldMap = new WorldMap(data);
        Simulation simulation = new Simulation(worldMap, this, data);

        Thread simThread = new Thread(simulation);
        simThread.setDaemon(true);
        simThread.start();
        startButton.setOnAction(actionEvent -> {
            startButton.setDisable(true);
            stopButton.setDisable(false);
            simulation.setIsRunning(true);
        });
      stopButton.setOnAction(actionEvent -> {
            startButton.setDisable(false);
            stopButton.setDisable(true);
            simulation.setIsRunning(false);
        });


    }

    public void drawMap(WorldMap worldMap){
        mapGrid.setHeight(500);
        mapGrid.setWidth(500);
        GraphicsContext graphics = mapGrid.getGraphicsContext2D();
        graphics.setFill(Color.GOLD);
        graphics.setStroke(Color.BLACK);
        graphics.setLineWidth(2.0);
        graphics.fillRect(0, 0, mapGrid.getWidth(), mapGrid.getHeight());
//        Boundary boundary = worldMap.getCurrentBoundary();
        for (int x = 0; x < mapGrid.getWidth() + 1; x += 20) {
            graphics.strokeLine(x + 10, 0, x + 10, mapGrid.getHeight());  // BORDER_OFFSET = BORDER_WIDTH / 2
        }
        for (int y = 0; y < mapGrid.getHeight() + 1; y += 20) {
            graphics.strokeLine(0, y + 10, mapGrid.getWidth(), y + 10);  // BORDER_OFFSET = BORDER_WIDTH / 2
        }
        List<WorldElement> worldElements = new ArrayList<>();
        worldElements.addAll(worldMap.getAnimalLiveList());
        worldElements.addAll(worldMap.getPlants().values());

        for (WorldElement worldElement : worldElements){
            graphics.strokeText(worldElement.toString(),
                    worldElement.getPosition().getX() * 20 + 15,
                    mapGrid.getHeight() - worldElement.getPosition().getY() * 20 - 10,
                    10);
        }
//        graphics.setTextAlign(TextAlignment.CENTER);
//        graphics.setTextBaseline(VPos.CENTER);
//        graphics.setFont(new Font("Arial", size));
//        graphics.setFill(black);
    }

}
