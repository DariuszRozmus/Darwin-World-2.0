import model.Vector2d;
import model.elements.Herbivore;
import model.elements.Plant;
import model.map.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Simulation implements Runnable{

    private WorldMap worldMap;
    private RandomPositionGenerator randomPositionGenerator;
    private RandomAnimalGenerator randomAnimalGenerator = new RandomAnimalGenerator();
    private AnimalWaiter animalWaiter = new AnimalWaiter();

    private static final int DAY_DECREASE_ENERGY = 10;
    private static final int PLANT_ENERGY = 20;
    private static final int PLANTS_PER_DAY = 5;

    private int startAnimals;
    private int startGeneLengthAnimals;
    private int startEnergyAnimal;
    private int day = 0;
    private final Breeder breeder = new Breeder();
    private final Planter planter = new Planter();


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

        while (day < 1000 && !worldMap.getAnimalLiveList().isEmpty()){

            removeDeathAnimals();
            moveLiveAnimals();
            eatPlants();
            reproduceAnimals();
            decreaseAnimalEnergy();
            plantNewPlants();
            System.out.println(worldMap);
            worldMap.getAnimalLiveList().forEach(animal -> System.out.println(animal.getPosition() + " Energy: " + animal.getEnergy()));
            System.out.println(worldMap.getAnimalLiveList());
            System.out.println(worldMap.getAnimalsDiedList());
            System.out.println("Day" + day);
            nextDay();
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
                .forEach(animal -> animal.decreaseEnergy(DAY_DECREASE_ENERGY));
    }

    private void plantNewPlants(){
        planter.plant(PLANTS_PER_DAY, PLANT_ENERGY, worldMap);
    }
}
