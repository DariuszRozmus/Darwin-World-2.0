package darwin.config;

import darwin.model.Vector2d;

import java.util.List;

public class DataValidator {

    public PreliminaryData validate(InputData data) {

        int worldWidth = parseInt(data.worldWidth(), "worldWidth");
        int worldHeight = parseInt(data.worldHeight(), "worldHeight");
        int jungleDownLeftCornerX = parseInt(data.jungleDownLeftCornerX(), "jungleDownLeftX");
        int jungleDownLeftCornerY = parseInt(data.jungleDownLeftCornerY(), "jungleDownLeftY");
        int jungleUpRightCornerX = parseInt(data.jungleUpRightCornerX(), "jungleUpRightX");
        int jungleUpRightCornerY = parseInt(data.jungleUpRightCornerY(), "jungleUpRightY");
        boolean isFOMO = parseBoolean(data.isFOMO(), "isFOMO");
        int fomoGroupSize = parseInt(data.fomoGroupSize(), "fomoGroupSize");
        int fomoRay = parseInt(data.fomoRay(), "fomoRay");
        boolean areCarnivoresPresent = parseBoolean(data.areCarnivoresPresent(), "areCarnivoresPresent");
        int initialCarnivoresCount = parseInt(data.initialCarnivoresCount(), "initialCarnivoresCount");
//        boolean areOmnivoresPresent = parseBoolean(data.areOmnivoresPresent(), "areOmnivoresPresent");
        int initialAnimalCount = parseInt(data.initialAnimalCount(), "initialAnimalCount");
        int initialJunglePlantCount = parseInt(data.initialJunglePlantCount(), "initialJunglePlantCount");
        int initialSavannaPlantCount = parseInt(data.initialSavannaPlantCount(), "initialSavannaPlantCount");
        int dailyNewPlantsCount = parseInt(data.dailyNewPlantsCount(), "dailyNewPlantsCount");
        int energyToReproduce = parseInt(data.energyToReproduce(), "energyToReproduce");
        int initialAnimalEnergy = parseInt(data.initialAnimalEnergy(), "initialAnimalEnergy");
        int initialGenesLength = parseInt(data.initialGenesLength(), "initialGenesLength");
        int dailyEnergyCost = parseInt(data.dailyEnergyCost(), "dailyEnergyCost");
        int plantEnergyValue = parseInt(data.plantEnergyValue(), "plantEnergyValue");
        int reproductionEnergyFactor = parseInt(data.reproductionEnergyFactor(), "reproductionEnergyFactor");
        int minMutationCount = parseInt(data.minMutationCount(), "minMutationCount");
        int maxMutationCount = parseInt(data.maxMutationCount(), "maxMutationCount");

        return new PreliminaryData(
            worldWidth,
            worldHeight,
            new Vector2d(jungleDownLeftCornerX, jungleDownLeftCornerY),
            new Vector2d(jungleUpRightCornerX, jungleUpRightCornerY),
            isFOMO,
            fomoGroupSize,
            fomoRay,
            areCarnivoresPresent,
            initialCarnivoresCount,
//            areOmnivoresPresent,
            initialAnimalCount,
            initialJunglePlantCount,
            initialSavannaPlantCount,
            dailyNewPlantsCount,
            energyToReproduce,
            initialAnimalEnergy,
            initialGenesLength,
            dailyEnergyCost,
            plantEnergyValue,
            reproductionEnergyFactor,
            minMutationCount,
            maxMutationCount
        );
    }
    private static boolean parseBoolean(
            String value,
            String fieldName){
        if (value == null || value.isBlank()) {
            throw new InvalidDataException(fieldName, fieldName + " cannot be empty");
        }

        return switch (value.trim().toLowerCase()) {
            case "true", "1", "yes", "on" -> true;
            case "false", "0", "no", "off" -> false;
            default -> {
                throw new InvalidDataException(fieldName, fieldName + " must be a boolean value");
            }
        };
    }

    private static int parseInt(
            String value,
            String fieldName){
        if (value == null || value.isBlank()) {
            throw new InvalidDataException(fieldName, fieldName + " cannot be empty");
        }
        try {
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException e) {
            throw new InvalidDataException(fieldName, fieldName + " must be an integer");
        }
    }

}
