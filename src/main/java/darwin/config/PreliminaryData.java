package darwin.config;

import darwin.model.Vector2d;

import darwin.model.Vector2d;

public record PreliminaryData(int worldWidth, int worldHeight,
                              Vector2d jungleDownLeftCorner, Vector2d jungleUpRightCorner,
                              boolean isFOMO,
                              int circleRay,
                              boolean areCarnivoresPresent,
                              int initialCarnivoresCount,
                              boolean areOmnivoresPresent,
                              int initialAnimalCount,
                              int initialJunglePlantCount, int initialSavannaPlantCount,
                              int dailyNewPlantsCount,
                              int energyToReproduce,
                              int initialAnimalEnergy, int initialGenesLength,
                              int dailyEnergyCost, int plantEnergyValue,
                              int reproductionEnergyFactor,
                              int minMutationCount, int maxMutationCount
                               ){
}
