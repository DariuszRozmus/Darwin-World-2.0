import model.elements.Herbivore;
import model.map.RandomAnimalGenerator;
import model.map.RandomPositionGenerator;
import model.map.WorldMap;

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
                        -> worldMap.place(
                                new Herbivore(0,
                                        startEnergyAnimal,
                                        randomAnimalGenerator.getGeneQueue(startGeneLengthAnimals),
                                        randomAnimalGenerator.getMapDirection(),
                                        vector2d)));
        System.out.println(worldMap);
    }
}
