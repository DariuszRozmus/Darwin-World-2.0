package darwin.config;

public record InputData(
        String worldWidth, String worldHeight,
        String jungleDownLeftCornerX, String jungleDownLeftCornerY,
        String jungleUpRightCornerX, String jungleUpRightCornerY,
        String isFOMO,
        String fomoGroupSize,
        String fomoRay,
        String areCarnivoresPresent,
        String initialCarnivoresCount,
//        String areOmnivoresPresent,
        String initialAnimalCount,
        String initialJunglePlantCount,
        String initialSavannaPlantCount,
        String dailyNewPlantsCount,
        String energyToReproduce,
        String initialAnimalEnergy,
        String initialGenesLength,
        String dailyEnergyCost,
        String plantEnergyValue,
        String reproductionEnergyFactor,
        String minMutationCount, String maxMutationCount
){
}