import model.elements.Herbivore;
import model.map.AnimalWaiter;
import model.map.RandomAnimalGenerator;
import model.map.RandomPositionGenerator;
import model.map.WorldMap;

public class Simulation implements Runnable{

    private WorldMap worldMap;
    private RandomPositionGenerator randomPositionGenerator;
    private RandomAnimalGenerator randomAnimalGenerator = new RandomAnimalGenerator();
    private AnimalWaiter animalWaiter = new AnimalWaiter();

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
            worldMap.getAnimalLiveList()
                    .removeIf(animal -> {
                        if(!animal.isLive()){
                            worldMap.removeFromMap(animal);
                            worldMap.registerDeath(animal);
                            return true;
                        }
                        return false;
                    });
            worldMap.getAnimalLiveList()
                    .forEach(animal -> worldMap.move(animal));
            //TODO eat
//            worldMap.getPlants().keySet().stream()
//                    .filter(position ->
//                            animalWaiter.canEat(worldMap.getAnimalsMap().get(position))
//                    ).
//
//            );
            worldMap.getAnimalLiveList()
                    .forEach(animal -> animal.decreaseEnergy(worldMap.getAnimalLiveList().indexOf(animal)+1));
            System.out.println(worldMap);
            worldMap.getAnimalLiveList().forEach(animal -> System.out.println(animal.getPosition()));
        }
    }
}
