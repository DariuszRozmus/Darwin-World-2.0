package darwin.model;

import darwin.config.PreliminaryData;
import darwin.model.elements.Animal;
import darwin.model.elements.Species;
import darwin.model.map.*;
import darwin.presenter.SimulationListener;
import darwin.presenter.SimulationPresenter;
import javafx.application.Platform;

import java.util.Map;

public class Simulation implements Runnable{

    private WorldMap worldMap;
    private RandomAnimalGenerator randomAnimalGenerator = new RandomAnimalGenerator();
    private AnimalWaiter animalWaiter = new AnimalWaiter();
    private Butcher butcher = new Butcher();

    private final int dayDecreaseEnergy;
    private final int plantEnergy;
    private final int newPlantsPerDay;

    private int startHerbivores;
    private int startGeneLengthAnimals;
    private int startEnergyAnimal;
    private int day = 0;
    private final Planter planter = new Planter();
    private final Breeder breeder;
    private final SimulationPresenter simulationPresenter;
    private volatile boolean isRunning = false;
    private final SimulationListener listener;
    private final PreliminaryData data;

    public Simulation(WorldMap worldMap, SimulationPresenter simulationPresenter,
                      PreliminaryData data, SimulationListener listener){
        this.data = data;
        this.listener = listener;
        this.simulationPresenter = simulationPresenter;
        this.worldMap = worldMap;
        this.startHerbivores = data.initialAnimalCount();
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

    public void setIsRunning(boolean running) {
        isRunning = running;
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

        while (!worldMap.getAnimalLiveList().isEmpty()) {
            if (!isRunning) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
                continue;
            }

            removeDeathAnimals();
            moveLiveAnimals();
            eatPlants();
            if(data.areCarnivoresPresent()) {
                letHuntersHunt();
            }
            reproduceAnimals();
            decreaseAnimalEnergy();
            plantNewPlants();
            System.out.println(worldMap);
            worldMap.getAnimalLiveList().forEach(animal -> System.out.println(animal.getPosition() + " Energy: " + animal.getEnergy()));

            System.out.println(worldMap.getAnimalLiveList());
            System.out.println(worldMap.getAnimalsDiedList());
            System.out.println("Day" + day);
            nextDay();
            Platform.runLater(() ->
                    listener.onDayUpdate(day, worldMap));
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

        }
        System.out.println("All animals died at day: " + day);
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
                        animal.setDeathDay(day);
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

    private void letHuntersHunt(){
        worldMap.getAnimalsMap().keySet().stream()
                .map(position -> worldMap.getAnimalsMap().get(position))
                .filter(animalList -> animalList.stream()
                        .anyMatch(animal -> animal.getSpecie() == Species.CARNIVORE) &&
                        animalList.stream()
                                .anyMatch(animal -> animal.getSpecie() == Species.HERBIVORE))
                .forEach(animalList -> butcher.killAndFeed(animalList));
    }
    private void decreaseAnimalEnergy(){
        worldMap.getAnimalLiveList()
                .forEach(animal -> animal.decreaseEnergy(dayDecreaseEnergy));
    }

    private void plantNewPlants(){
        planter.plant(newPlantsPerDay, plantEnergy, worldMap);
    }
}
