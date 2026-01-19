<<<<<<< Updated upstream
package darwin.model.map;
=======
package darwin.model;
>>>>>>> Stashed changes

import darwin.config.PreliminaryData;
import darwin.model.elements.Animal;
import darwin.model.elements.Species;
<<<<<<< Updated upstream
=======
import darwin.model.map.*;
import darwin.presenter.SimulationPresenter;
>>>>>>> Stashed changes

import java.util.Map;

public class Simulation implements Runnable{

    private WorldMap worldMap;
    private RandomPositionGenerator randomPositionGenerator;
    private RandomAnimalGenerator randomAnimalGenerator = new RandomAnimalGenerator();
    private AnimalWaiter animalWaiter = new AnimalWaiter();

    private final int dayDecreaseEnergy;
    private final int plantEnergy;
    private final int newPlantsPerDay;

    private int startAnimals;
    private int startGeneLengthAnimals;
    private int startEnergyAnimal;
    private int day = 0;
    private final Planter planter = new Planter();
    private final Breeder breeder;
<<<<<<< Updated upstream


    public Simulation(WorldMap worldMap, PreliminaryData data){
=======
    private final SimulationPresenter simulationPresenter;


    public Simulation(WorldMap worldMap, SimulationPresenter simulationPresenter, PreliminaryData data){
        this.simulationPresenter = simulationPresenter;
>>>>>>> Stashed changes
        this.worldMap = worldMap;
        this.startAnimals = data.initialAnimalCount();
        this.startEnergyAnimal = data.initialAnimalEnergy();
        this.startGeneLengthAnimals = data.initialGenesLength();
        this.dayDecreaseEnergy = data.dailyEnergyCost();
        this.plantEnergy = data.plantEnergyValue();
        this.newPlantsPerDay = data.dailyNewPlantsCount();
        this.breeder = new Breeder(data.energyToReproduce(), data.reproductionEnergyFactor(),
                data.minMutationCount(), data.maxMutationCount());
        this.randomPositionGenerator =
                new RandomPositionGenerator(worldMap.getMapUpCorner().getX(),
                worldMap.getMapUpCorner().getY(), startAnimals);
    }

    public void run() {
        randomPositionGenerator.iterator()
                .forEachRemaining(vector2d
                        -> worldMap.addToMap(
                                new Animal(day,
                                        startEnergyAnimal, Species.HERBIVORE,
                                        randomAnimalGenerator.getGeneQueue(startGeneLengthAnimals),
                                        randomAnimalGenerator.getMapDirection(),
                                        vector2d, worldMap)));
        System.out.println(worldMap);

        while (day < 1000 && !worldMap.getAnimalLiveList().isEmpty()){

            removeDeathAnimals();
            simulationPresenter.drawMap(worldMap);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            moveLiveAnimals();
            simulationPresenter.drawMap(worldMap);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            eatPlants();
            simulationPresenter.drawMap(worldMap);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            reproduceAnimals();
            simulationPresenter.drawMap(worldMap);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            decreaseAnimalEnergy();
            simulationPresenter.drawMap(worldMap);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            plantNewPlants();
            simulationPresenter.drawMap(worldMap);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.out.println(worldMap);
            worldMap.getAnimalLiveList().forEach(animal -> System.out.println(animal.getPosition() + " Energy: " + animal.getEnergy()));

            simulationPresenter.drawMap(worldMap);
            System.out.println(worldMap.getAnimalLiveList());
            System.out.println(worldMap.getAnimalsDiedList());
            System.out.println("Day" + day);
            nextDay();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
//        System.out.println("All animals died at day: " + day);
    }

    private void nextDay(){
        day ++;
    }

    public int getDay(){
        return day;
    }

    private void removeDeathAnimals(){
        worldMap.getAnimalLiveList()
                .removeIf(animal -> {
                    if(!animal.isLive()){
                        worldMap.removeFromMap(animal);
                        worldMap.registerDeath(animal);
                        return true;
                    }
                    return false;
                });
    }

    private void moveLiveAnimals(){
        worldMap.getAnimalLiveList()
                .forEach(animal -> worldMap.move(animal));
    }

    private void eatPlants() {
        worldMap.getPlants().entrySet().stream()
                .filter(entry -> {
                    var animals = worldMap.getAnimalsMap().get(entry.getKey());
                    return animals != null
                            && !animals.isEmpty()
                            && animalWaiter.canEat(animals);
                })
                .map(Map.Entry::getValue)
                .toList()
                .forEach(plant ->
                        worldMap.eatPlantAt(plant.getPosition(), animalWaiter)
                );
    }

    private void reproduceAnimals(){
        worldMap.getAnimalsMap().keySet().stream()
                .map(position -> worldMap.getAnimalsMap().get(position))
                .filter(animalList -> animalList.size() >= 2)
                .forEach(animalList -> breeder.breedBest(animalList, day, worldMap));
    }
    private void decreaseAnimalEnergy(){
        worldMap.getAnimalLiveList()
                .forEach(animal -> animal.decreaseEnergy(dayDecreaseEnergy));
    }

    private void plantNewPlants(){
        planter.plant(newPlantsPerDay, plantEnergy, worldMap);
    }
}
