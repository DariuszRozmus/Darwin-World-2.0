<<<<<<< Updated upstream
package config;

import model.Vector2d;
=======
package darwin.config;

import darwin.model.Vector2d;
>>>>>>> Stashed changes

public record PreliminaryData(int worldWidth, int worldHeight,
                              Vector2d jungleDownLeftCorner, Vector2d jungleUpRightCorner,
                              boolean isFOMO,
                              boolean areCarnivoresPresent,
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
