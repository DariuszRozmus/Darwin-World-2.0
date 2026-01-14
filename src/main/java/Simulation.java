import model.Vector2d;
import model.elements.Herbivore;
import model.elements.Plant;
import model.map.AnimalWaiter;
import model.map.RandomAnimalGenerator;
import model.map.RandomPositionGenerator;
import model.map.WorldMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Simulation implements Runnable{

    private WorldMap worldMap;
    private RandomPositionGenerator randomPositionGenerator;
    private RandomAnimalGenerator randomAnimalGenerator = new RandomAnimalGenerator();
    private AnimalWaiter animalWaiter = new AnimalWaiter();

    private int startAnimals;
    private int startGeneLengthAnimals;
    private int startEnergyAnimal;
    private int day = 0;


    public Simulation(WorldMap worldMap, int startAnimals, int startEnergyAnimal, int startGeneLengthAnimals){
        this.worldMap = worldMap;
        this.startAnimals = startAnimals;
        this.startEnergyAnimal = startEnergyAnimal;
        this.startGeneLengthAnimals = startGeneLengthAnimals;
        this.randomPositionGenerator =
                new RandomPositionGenerator(worldMap.getMapUpCorner().getX(),
                worldMap.getMapUpCorner().getY(), startAnimals);
    }

    public void run() {
        randomPositionGenerator.iterator()
                .forEachRemaining(vector2d
                        -> worldMap.addToMap(
                                new Herbivore(day,
                                        startEnergyAnimal,
                                        randomAnimalGenerator.getGeneQueue(startGeneLengthAnimals),
                                        randomAnimalGenerator.getMapDirection(),
                                        vector2d, worldMap)));
        System.out.println(worldMap);

        for (int i = 0; i < 20; i++){
            removeDeathAnimals();
            moveLiveAnimals();
            eatPlants();
            decreaseAnimalEnergy();
            System.out.println(worldMap);
            worldMap.getAnimalLiveList().forEach(animal -> System.out.println(animal.getEnergy()));
            nextDay();
        }
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

    private void decreaseAnimalEnergy(){
        worldMap.getAnimalLiveList()
                .forEach(animal -> animal.decreaseEnergy(worldMap.getAnimalLiveList().indexOf(animal)+1));
    }
}
