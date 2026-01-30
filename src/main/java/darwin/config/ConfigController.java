package darwin.config;

import darwin.model.Vector2d;
import impl.jfxtras.styles.jmetro.ToggleSwitchSkin;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.AccessibleAttribute;
import javafx.scene.control.*;
import org.controlsfx.control.ToggleSwitch;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import static java.lang.Math.max;

public class ConfigController {

    private Consumer<PreliminaryData> simulationStarter;
    private DataValidator dataValidator = new DataValidator();
    private Map<String, Control> fieldMap = new HashMap<>();

    @FXML
    private Slider worldWidth;

    @FXML
    private Slider worldHeight;

    @FXML
    private Slider jungleDownLeftCornerX;

    @FXML
    private Slider jungleDownLeftCornerY;

    @FXML
    private Slider jungleUpRightCornerX;

    @FXML
    private Slider jungleUpRightCornerY;

    @FXML
    private ToggleSwitch isFOMO;

    @FXML
    private TextField fomoGroupSize;

    @FXML
    private Slider fomoRay;

    @FXML
    private ToggleSwitch areCarnivoresPresent;

    @FXML
    private TextField initialCarnivoresCount;

//    @FXML
//    private ToggleSwitch areOmnivoresPresent;

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

        Tooltip tooltip = new Tooltip("To pole jest wymagane");
        Tooltip.install(worldWidth, tooltip);

        fieldMap = Map.ofEntries(
                Map.entry("worldWidth", worldWidth),
                Map.entry("worldHeight", worldHeight),
                Map.entry("jungleDownLeftX", jungleDownLeftCornerX),
                Map.entry("jungleDownLeftY", jungleDownLeftCornerY),
                Map.entry("jungleUpRightX", jungleUpRightCornerX),
                Map.entry("jungleUpRightY", jungleUpRightCornerY),
                Map.entry("isFOMO", isFOMO),
                Map.entry("fomoGroupSize", fomoGroupSize),
                Map.entry("fomoRay", fomoRay),
                Map.entry("areCarnivoresPresent", areCarnivoresPresent),
                Map.entry("initialCarnivoresCount", initialCarnivoresCount),
//                Map.entry("areOmnivoresPresent", areOmnivoresPresent),
                Map.entry("initialAnimalCount", initialAnimalCount),
                Map.entry("initialJunglePlantCount", initialJunglePlantCount),
                Map.entry("initialSavannaPlantCount", initialSavannaPlantCount),
                Map.entry("dailyNewPlantsCount", dailyNewPlantsCount),
                Map.entry("energyToReproduce", energyToReproduce),
                Map.entry("initialAnimalEnergy", initialAnimalEnergy),
                Map.entry("initialGenesLength", initialGenesLength),
                Map.entry("dailyEnergyCost", dailyEnergyCost),
                Map.entry("plantEnergyValue", plantEnergyValue),
                Map.entry("reproductionEnergyFactor", reproductionEnergyFactor),
                Map.entry("minMutationCount", minMutationCount),
                Map.entry("maxMutationCount", maxMutationCount)
        );


        startButton.setOnAction(event -> onStartClicked());
        startDefaultButton.setOnAction(event -> onDefaultClicked());

        worldWidth.valueProperty().addListener((obs, oldVal, newVal) -> {
            int width = newVal.intValue();
            jungleUpRightCornerX.setMax(width);

            if ((int) jungleUpRightCornerX.getValue() > width) {
                jungleUpRightCornerX.setValue(width);
            }
        });
        jungleUpRightCornerX.setMax((int) jungleUpRightCornerX.getValue());

        worldHeight.valueProperty().addListener((obs, oldVal, newVal) -> {
            int width = newVal.intValue();
            jungleUpRightCornerY.setMax(width);

            if ((int) jungleUpRightCornerY.getValue() > width) {
                jungleUpRightCornerY.setValue(width);
            }
        });
        jungleUpRightCornerY.setMax((int) jungleUpRightCornerY.getValue());

        jungleUpRightCornerX.valueProperty().addListener((obs, oldVal, newVal) -> {
            int width = newVal.intValue();
            jungleDownLeftCornerX.setMax(width);

            if ((int) jungleDownLeftCornerX.getValue() > width) {
                jungleDownLeftCornerX.setValue(width);
            }
        });
        jungleDownLeftCornerX.setMax((int) jungleDownLeftCornerX.getValue());

        jungleUpRightCornerY.valueProperty().addListener((obs, oldVal, newVal) -> {
            int width = newVal.intValue();
            jungleDownLeftCornerY.setMax(width);

            if ((int) jungleDownLeftCornerY.getValue() > width) {
                jungleDownLeftCornerY.setValue(width);
            }
        });
        jungleDownLeftCornerY.setMax((int) jungleDownLeftCornerY.getValue());

        areCarnivoresPresent.selectedProperty().addListener((obs, wasSelected, isNowSelected) -> {
            if (isNowSelected) {
                initialCarnivoresCount.setDisable(false);
            } else {
                initialCarnivoresCount.setDisable(true);
            }
        });

        isFOMO.selectedProperty().addListener((obs, wasSelected, isNowSelected) -> {
            if (isNowSelected) {
                fomoGroupSize.setDisable(false);
                fomoRay.setDisable(false);
            } else {
                fomoGroupSize.setDisable(true);
                fomoRay.setDisable(true);
            }
        });

    }

    @FXML
    private void onDefaultClicked() {
        PreliminaryData data = new PreliminaryData(6,6,
                new Vector2d(2,2),new Vector2d(4,4),
                false, 0, 0,
                false, 0,
                5,
                5,5,
                3,10,
                30,10, 2,20,
                4,2, 6);
        simulationStarter.accept(data);
    }

    @FXML
    private void onStartClicked() {
        InputData inputData = collectRowData();
        PreliminaryData data = validateData(inputData);
        simulationStarter.accept(data);
    }

    private InputData collectRowData() {
        return new InputData(
                String.valueOf((int) worldWidth.getValue()),
                String.valueOf((int) worldHeight.getValue()),
                String.valueOf((int) jungleDownLeftCornerX.getValue()),
                String.valueOf((int) jungleDownLeftCornerY.getValue()),
                String.valueOf((int) jungleUpRightCornerX.getValue()),
                String.valueOf((int) jungleUpRightCornerY.getValue()),
                String.valueOf(isFOMO.isSelected()),
                fomoGroupSize.getText(),
                String.valueOf((int) fomoRay.getValue()),
                String.valueOf(areCarnivoresPresent.isSelected()),
                initialCarnivoresCount.getText(),
//                areOmnivoresPresent.getText(),
                initialAnimalCount.getText(),
                initialJunglePlantCount.getText(),
                initialSavannaPlantCount.getText(),
                dailyNewPlantsCount.getText(),
                energyToReproduce.getText(),
                initialAnimalEnergy.getText(),
                initialGenesLength.getText(),
                dailyEnergyCost.getText(),
                plantEnergyValue.getText(),
                reproductionEnergyFactor.getText(),
                minMutationCount.getText(),
                maxMutationCount.getText()
        );
    }

    private PreliminaryData validateData(InputData inputData) {
        try{
            return dataValidator.validate(inputData);
        } catch (InvalidDataException e){
            System.out.println("Invalid input data: " + e.getMessage());
            markFieldAsError(e.getField(), e.getMessage());
            return null;
        }
    }

    private void markFieldAsError(String fieldName, String message){
        Control control = fieldMap.get(fieldName);
        if(control != null){
            control.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
        }
    }
}
