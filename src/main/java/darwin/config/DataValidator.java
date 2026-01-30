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

        if (worldWidth <= 0) {
            throw new InvalidDataException("worldWidth", "worldWidth must be greater than 0");
        }
        if (worldHeight <= 0) {
            throw new InvalidDataException("worldHeight", "worldHeight must be greater than 0");
        }
        if (jungleDownLeftCornerX < 0) {
            throw new InvalidDataException("jungleDownLeftX", "jungleDownLeftX must be non-negative");
        }
        if (jungleDownLeftCornerY < 0) {
            throw new InvalidDataException("jungleDownLeftY", "jungleDownLeftY must be non-negative");
        }
        if (jungleUpRightCornerX > worldWidth) {
            throw new InvalidDataException("jungleUpRightX", "jungleUpRightX must be less than or equal to worldWidth");
        }
        if (jungleUpRightCornerY > worldHeight) {
            throw new InvalidDataException("jungleUpRightY", "jungleUpRightY must be less than or equal to worldHeight");
        }
        if (jungleDownLeftCornerX >= jungleUpRightCornerX) {
            throw new InvalidDataException("jungleDownLeftX and jungleUpRightX", "jungleDownLeftX must be less than jungleUpRightX");
        }
        if (jungleDownLeftCornerY >= jungleUpRightCornerY) {
            throw new InvalidDataException("jungleDownLeftY and jungleUpRightY", "jungleDownLeftY must be less than jungleUpRightY");
        }
        if (isFOMO) {
            if (fomoGroupSize <= 0) {
                throw new InvalidDataException("fomoGroupSize", "fomoGroupSize must be greater than 0 when FOMO is enabled");
            }
            if (fomoRay <= 0) {
                throw new InvalidDataException("fomoRay", "fomoRay must be greater than 0 when FOMO is enabled");
            }
        }
        if (areCarnivoresPresent && initialCarnivoresCount <= 0) {
            throw new InvalidDataException("initialCarnivoresCount", "initialCarnivoresCount must be greater than 0 when carnivores are present");
        }
        if (initialAnimalCount <= 0) {
            throw new InvalidDataException("initialAnimalCount", "initialAnimalCount must be greater than 0");
        }
        if (initialJunglePlantCount < 0) {
            throw new InvalidDataException("initialJunglePlantCount", "initialJunglePlantCount cannot be negative");
        }
        if (initialSavannaPlantCount < 0) {
            throw new InvalidDataException("initialSavannaPlantCount", "initialSavannaPlantCount cannot be negative");
        }
        if (dailyNewPlantsCount < 0) {
            throw new InvalidDataException("dailyNewPlantsCount", "dailyNewPlantsCount cannot be negative");
        }
        if (energyToReproduce <= 0) {
            throw new InvalidDataException("energyToReproduce", "energyToReproduce must be greater than 0");
        }
        if (initialAnimalEnergy <= 0) {
            throw new InvalidDataException("initialAnimalEnergy", "initialAnimalEnergy must be greater than 0");
        }
        if (initialGenesLength <= 0) {
            throw new InvalidDataException("initialGenesLength", "initialGenesLength must be greater than 0");
        }
        if (dailyEnergyCost < 0) {
            throw new InvalidDataException("dailyEnergyCost", "dailyEnergyCost cannot be negative");
        }
        if (plantEnergyValue <= 0) {
            throw new InvalidDataException("plantEnergyValue", "plantEnergyValue must be greater than 0");
        }
        if (reproductionEnergyFactor <= 0) {
            throw new InvalidDataException("reproductionEnergyFactor", "reproductionEnergyFactor must be greater than 0");
        }
        if (minMutationCount < 0) {
            throw new InvalidDataException("minMutationCount", "minMutationCount cannot be negative");
        }
        if (maxMutationCount < minMutationCount) {
            throw new InvalidDataException("maxMutationCount", "maxMutationCount must be greater than or equal to minMutationCount");
        }


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
