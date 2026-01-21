package darwin.config;

import darwin.model.Vector2d;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.util.function.Consumer;

public class ConfigController {

    private Consumer<PreliminaryData> simulationStarter;

    @FXML
    private TextField worldWidth;

    @FXML
    private TextField worldHeight;

    @FXML
    private TextField jungleDownLeftCornerX;

    @FXML
    private TextField jungleDownLeftCornerY;

    @FXML
    private TextField jungleUpRightCornerX;

    @FXML
    private TextField jungleUpRightCornerY;

    @FXML
    private TextField isFOMO;
    @FXML
    private TextField areCarnivoresPresent;

    @FXML
    private TextField areOmnivoresPresent;

    @FXML
    private TextField initialAnimalCount;
    @FXML
    private TextField initialJunglePlantCount;

    @FXML
    private TextField initialSavannaPlantCount;

    @FXML
    private TextField dailyNewPlantsCount;

    @FXML
    private TextField energyToReproduce;

    @FXML
    private TextField initialAnimalEnergy;

    @FXML
    private TextField initialGenesLength;

    @FXML
    private TextField dailyEnergyCost;

    @FXML
    private TextField plantEnergyValue;

    @FXML
    private TextField reproductionEnergyFactor;

    @FXML
    private TextField minMutationCount;

    @FXML
    private TextField maxMutationCount;


    @FXML
    private Button startButton;

    @FXML
    private Button startDefaultButton;

    public void setSimulationStarter(Consumer<PreliminaryData> starter) {
        this.simulationStarter = starter;
    }

    @FXML
    void initialize() {
        startButton.setOnAction(event -> onStartClicked());
        startDefaultButton.setOnAction(event -> onDefaultClicked());
    }

    @FXML
    private void onDefaultClicked() {
        PreliminaryData data = new PreliminaryData(6,6,
                new Vector2d(2,2),new Vector2d(4,4),
                false, false, false,
                5,
                5,5,
                3,10,
                30,10, 2,20,
                4,2, 6);
        simulationStarter.accept(data);
    }

    @FXML
    private void onStartClicked() {
        PreliminaryData data = collectDataFromForm();
        simulationStarter.accept(data);
    }

    private PreliminaryData collectDataFromForm() {
        return new PreliminaryData(
                Integer.parseInt(worldWidth.getText()),
                Integer.parseInt(worldHeight.getText()),
                new Vector2d(Integer.parseInt(jungleDownLeftCornerX.getText()),
                        Integer.parseInt(jungleDownLeftCornerY.getText())),
                new Vector2d(Integer.parseInt(jungleUpRightCornerX.getText()),
                        Integer.parseInt(jungleUpRightCornerY.getText())),
                Boolean.getBoolean(isFOMO.getText()),
                Boolean.getBoolean(areCarnivoresPresent.getText()),
                false, //Boolean.getBoolean(areOmnivoresPresent.getText()),
                Integer.parseInt(initialAnimalCount.getText()),
                Integer.parseInt(initialJunglePlantCount.getText()),
                Integer.parseInt(initialSavannaPlantCount.getText()),
                Integer.parseInt(dailyNewPlantsCount.getText()),
                Integer.parseInt(energyToReproduce.getText()),
                Integer.parseInt(initialAnimalEnergy.getText()),
                Integer.parseInt(initialGenesLength.getText()),
                Integer.parseInt(dailyEnergyCost.getText()),
                Integer.parseInt(plantEnergyValue.getText()),
                Integer.parseInt(reproductionEnergyFactor.getText()),
                Integer.parseInt(minMutationCount.getText()),
                Integer.parseInt(maxMutationCount.getText())
        );
    }
}
