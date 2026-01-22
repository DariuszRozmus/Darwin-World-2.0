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
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SimulationPresenter implements SimulationListener {

    @FXML
    private Canvas mapGrid;

    @FXML
    private Button startButton;

    @FXML
    private Button stopButton;

    @FXML
    private LineChart<Number, Number> populationChart;
    private XYChart.Series<Number, Number> animalsSeries;
    private XYChart.Series<Number, Number> plantsSeries;
    private XYChart.Series<Number, Number> freeFieldsSeries;
    private XYChart.Series<Number, Number> avridgeEnergySeries;
    private XYChart.Series<Number, Number> avridgeLiveLengthSeries;
    private XYChart.Series<Number, Number> avridgeChildrenSeries;


    public void init(PreliminaryData data) {
        setupChart();
        WorldMap worldMap = new WorldMap(data);
        Simulation simulation = new Simulation(worldMap, this, data,this);

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
        mapGrid.setHeight(25 * worldMap.getMapUpCorner().getX());
        mapGrid.setWidth(25 * worldMap.getMapUpCorner().getY());
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
                    mapGrid.getHeight() - worldElement.getPosition().getY() * 20 - 5,
                    10);
        }
//        graphics.setTextAlign(TextAlignment.CENTER);
//        graphics.setTextBaseline(VPos.CENTER);
//        graphics.setFont(new Font("Arial", size));
//        graphics.setFill(black);
    }
    private void setupChart() {
        animalsSeries = new XYChart.Series<>();
        animalsSeries.setName("Zwierzęta");

        plantsSeries = new XYChart.Series<>();
        plantsSeries.setName("Rośliny");

        freeFieldsSeries = new XYChart.Series<>();
        freeFieldsSeries.setName("Wolne pola");

        avridgeEnergySeries = new XYChart.Series<>();
        avridgeEnergySeries.setName("Średnia energia");

        avridgeLiveLengthSeries = new XYChart.Series<>();
        avridgeLiveLengthSeries.setName("Średnia długość życia");

        avridgeChildrenSeries = new XYChart.Series<>();
        avridgeChildrenSeries.setName("Średnia liczba dzieci");

        populationChart.getData().addAll(animalsSeries, plantsSeries, freeFieldsSeries,
                avridgeEnergySeries, avridgeLiveLengthSeries, avridgeChildrenSeries);
        populationChart.setAnimated(false);
    }

    @Override
    public void onDayUpdate(int day, WorldMap worldMap) {
        Platform.runLater(() -> {
            animalsSeries.getData().add(
                    new XYChart.Data<>(day, worldMap.getAnimalLiveList().size())
            );
            plantsSeries.getData().add(
                    new XYChart.Data<>(day, worldMap.getPlants().size())
            );
            freeFieldsSeries.getData().add(
                    new XYChart.Data<>(day, worldMap.getEmptyFieldsCount())
            );
            avridgeEnergySeries.getData().add(
                    new XYChart.Data<>(day, worldMap.getAverageEnergyOfLivingAnimals())
            );
            avridgeChildrenSeries.getData().add(
                    new XYChart.Data<>(day, worldMap.getAverageChildrenCountOfLivingAnimals())
            );
            avridgeLiveLengthSeries.getData().add(
                    new XYChart.Data<>(day, worldMap.getAverageLifeLengthOfDeadAnimals())
            );
            drawMap(worldMap);
        });
    }
}
