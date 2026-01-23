package darwin.config;

import darwin.model.Vector2d;
import impl.jfxtras.styles.jmetro.ToggleSwitchSkin;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.AccessibleAttribute;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import org.controlsfx.control.ToggleSwitch;

import java.util.function.Consumer;

import static java.lang.Math.max;

public class ConfigController {

    private Consumer<PreliminaryData> simulationStarter;

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

//        fomoProperty.bind(isFOMO.selectedProperty());
//        carnivoresProperty.bind(areCarnivoresPresent.selectedProperty());

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
                false, 5,
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
                (int) worldWidth.getValue(),
                (int) worldHeight.getValue(),
                new Vector2d((int) jungleDownLeftCornerX.getValue(),
                        (int) jungleDownLeftCornerY.getValue()),
                new Vector2d((int) jungleUpRightCornerX.getValue(),
                        (int) jungleUpRightCornerY.getValue()),
                isFOMO.isSelected(),
                intOrZero(fomoGroupSize),
                (int) fomoRay.getValue(),
                areCarnivoresPresent.isSelected(),
                intOrZero(initialCarnivoresCount),
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

    private int intOrZero(TextField field) {
        try {
            String text = field.getText();
            if (text == null || text.isBlank()) {
                return 0;
            }
            return Math.max(Integer.parseInt(text), 0);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

}
