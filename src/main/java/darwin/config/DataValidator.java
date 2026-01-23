package darwin.config;

import darwin.model.Vector2d;

import java.util.List;

public class DataValidator {

    public PreliminaryData validate(InputData data) {
        List<String> errors = new java.util.ArrayList<>();

        int worldWidth = parseInt(data.worldWidth(), "worldWidth", errors);
        int worldHeight = parseInt(data.worldHeight(), "worldHeight", errors);
        int jungleDownLeftCornerX = parseInt(data.jungleDownLeftCornerX(), "jungleDownLeftX", errors);
        int jungleDownLeftCornerY = parseInt(data.jungleDownLeftCornerY(), "jungleDownLeftY", errors);
        int jungleUpRightCornerX = parseInt(data.jungleUpRightCornerX(), "jungleUpRightX", errors);
        int jungleUpRightCornerY = parseInt(data.jungleUpRightCornerY(), "jungleUpRightY", errors);
        boolean isFOMO = parseBoolean(data.isFOMO(), "isFOMO", errors);
        int fomoGroupSize = parseInt(data.fomoGroupSize(), "fomoGroupSize", errors);
        int fomoRay = parseInt(data.fomoRay(), "fomoRay", errors);
        boolean areCarnivoresPresent = parseBoolean(data.areCarnivoresPresent(), "areCarnivoresPresent", errors);
        int initialCarnivoresCount = parseInt(data.initialCarnivoresCount(), "initialCarnivoresCount", errors);
        boolean areOmnivoresPresent = parseBoolean(data.areOmnivoresPresent(), "areOmnivoresPresent", errors);
        int initialAnimalCount = parseInt(data.initialAnimalCount(), "initialAnimalCount", errors);
        int initialJunglePlantCount = parseInt(data.initialJunglePlantCount(), "initialJunglePlantCount", errors);
        int initialSavannaPlantCount = parseInt(data.initialSavannaPlantCount(), "initialSavannaPlantCount", errors);
        int dailyNewPlantsCount = parseInt(data.dailyNewPlantsCount(), "dailyNewPlantsCount", errors);
        int energyToReproduce = parseInt(data.energyToReproduce(), "energyToReproduce", errors);
        int initialAnimalEnergy = parseInt(data.initialAnimalEnergy(), "initialAnimalEnergy", errors);
        int initialGenesLength = parseInt(data.initialGenesLength(), "initialGenesLength", errors);
        int dailyEnergyCost = parseInt(data.dailyEnergyCost(), "dailyEnergyCost", errors);
        int plantEnergyValue = parseInt(data.plantEnergyValue(), "plantEnergyValue", errors);
        int reproductionEnergyFactor = parseInt(data.reproductionEnergyFactor(), "reproductionEnergyFactor", errors);
        int minMutationCount = parseInt(data.minMutationCount(), "minMutationCount", errors);
        int maxMutationCount = parseInt(data.maxMutationCount(), "maxMutationCount", errors);

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
            areOmnivoresPresent,
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
            String fieldName,
            List<String> errors
    ) {
        if (value == null || value.isBlank()) {
            errors.add(fieldName + " cannot be empty");
            return false;
        }

        return switch (value.trim().toLowerCase()) {
            case "true", "1", "yes", "on" -> true;
            case "false", "0", "no", "off" -> false;
            default -> {
                errors.add(fieldName + " must be boolean");
                yield false;
            }
        };
    }

    private static int parseInt(
            String value,
            String fieldName,
            List<String> errors
    ) {
        if (value == null || value.isBlank()) {
            errors.add(fieldName + " cannot be empty");
            return 0;
        }

        try {
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException e) {
            errors.add(fieldName + " must be an integer");
            return 0;
        }
    }

}
