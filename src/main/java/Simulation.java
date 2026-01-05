import model.elements.Herbivore;
import model.map.RandomAnimalGenerator;
import model.map.RandomPositionGenerator;
import model.map.WorldMap;

import java.util.List;

public class Simulation implements Runnable{

    private WorldMap worldMap;
    private RandomPositionGenerator randomPositionGenerator;
    private RandomAnimalGenerator randomAnimalGenerator = new RandomAnimalGenerator();
    private int startAnimals;
    private int startGeneLengthAnimals;
    private int startEnergyAnimal;


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
                                new Herbivore(0,
                                        startEnergyAnimal,
                                        randomAnimalGenerator.getGeneQueue(startGeneLengthAnimals),
                                        randomAnimalGenerator.getMapDirection(),
                                        vector2d, worldMap)));
        System.out.println(worldMap);

        for (int i = 0; i < 22; i++){
            worldMap.getAnimalLiveList().stream()
                    .filter(animal -> !animal.isLive())
                    .forEach(animal -> worldMap.killAnimal(animal));
            worldMap.getAnimalsDiedList()
                    .forEach(animal -> worldMap.getAnimalLiveList().remove(animal));
            worldMap.getAnimalLiveList()
                    .forEach(animal -> worldMap.move(animal));
            worldMap.getAnimalLiveList()
                    .forEach(animal -> animal.decreaseEnergy(worldMap.getAnimalLiveList().indexOf(animal)+1));
            System.out.println(worldMap);
            worldMap.getAnimalLiveList().forEach(animal -> System.out.println(animal.getPosition()));
        }
    }
}
