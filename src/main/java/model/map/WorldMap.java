package model.map;

import model.Vector2d;
import model.elements.Animal;
import model.elements.Grass;
import model.elements.Plant;
import model.elements.WorldElement;

import java.util.*;

public class WorldMap implements MoveValidator {

    private Map<Vector2d, List<Animal>> animalsMap = new HashMap<Vector2d, List<Animal>>();
    private List<Animal> animalsLiveList = new ArrayList<>();
    private List<Animal> animalsDiedList = new ArrayList<>();
    private Map<Vector2d, Plant> plants = new HashMap<Vector2d, Plant>();
    private MapVisualizer mapVisualizer;
    
    private static final Vector2d DOWN_CORNER = new Vector2d(0,0);
    private final Vector2d mapUpCorner;
    private final Vector2d jungleDownCorner;
    private final Vector2d jungleUpCorner;
    private final int junglePlant;
    private final int mapPlant;
    private final Set<Vector2d> junglePositionsSet = new HashSet<>();
    private final Set<Vector2d> savannaPositionsSet = new HashSet<>();

    public WorldMap(Vector2d mapUpCorner, Vector2d jungleDownCorner, Vector2d jungleUpCorner,
                    int junglePlant, int mapPlant){
        this.mapUpCorner = mapUpCorner;
        this.jungleDownCorner = jungleDownCorner;
        this.jungleUpCorner = jungleUpCorner;
        this.junglePlant = junglePlant;
        this.mapPlant = mapPlant;
        this.mapVisualizer = new MapVisualizer(this);

        for (int x = jungleDownCorner.getX(); x <= jungleUpCorner.getX(); x++) {
            for (int y = jungleDownCorner.getY(); y <= jungleUpCorner.getY(); y++) {
                Vector2d position = new Vector2d(x, y);
                junglePositionsSet.add(position);
            }
        }

        for (int x = DOWN_CORNER.getX(); x <= mapUpCorner.getX(); x++) {
            for (int y = DOWN_CORNER.getY(); y <= mapUpCorner.getY(); y++) {
                Vector2d position = new Vector2d(x, y);
                if (!junglePositionsSet.contains(position)) {
                    savannaPositionsSet.add(position);
                }
            }
        }

        RandomPositionGenerator savannaRandomPositionGenerator =
                new RandomPositionGenerator(savannaPositionsSet, mapPlant);
        savannaRandomPositionGenerator.iterator()
                .forEachRemaining(vector2d -> plants.put(vector2d, new Grass(vector2d,20)));

        RandomPositionGenerator jungleRandomPositionGenerator = new RandomPositionGenerator(junglePositionsSet, junglePlant);
        jungleRandomPositionGenerator.iterator()
                .forEachRemaining(vector2d -> plants.put(vector2d, new Grass(vector2d,20)));
    }

    public boolean isOccupiedByPlant(Vector2d position){
        return plants.containsKey(position);
    }

    public boolean isOccupiedByAnimal(Vector2d position) {
        List<Animal> list = animalsMap.get(position);
        return list != null && !list.isEmpty();
    }

    public boolean isOccupied(Vector2d position){
        return isOccupiedByAnimal(position) || isOccupiedByPlant(position);
    }

    public WorldElement objectAt(Vector2d position){
        if (isOccupiedByAnimal(position)){
            return animalsMap.get(position).getFirst();
        } else // It's occupied by plant ->
            return plants.get(position);
    }

    public void addToMap(Animal animal){
        animalsMap
            .computeIfAbsent(animal.getPosition(), vector2d -> new ArrayList<>())
            .add(animal);
        animalsLiveList.add(animal);
    }

    public void place(Animal animal) {
        animalsMap
                .computeIfAbsent(animal.getPosition(), vector2d -> new ArrayList<>())
                .add(animal);
    }

    public boolean canMoveTo(Vector2d position){
        return isInBounds(position);
    }

    public void removeFromMap(Animal animal){
        this.removeAnimal(animal);
    }

    public void registerDeath(Animal animal){
        animalsDiedList.add(animal);
    }

    private boolean isInBounds(Vector2d position){
        return mapUpCorner.follows(position) && DOWN_CORNER.precedes(position);
    }

    public Vector2d getMapUpCorner(){
        return mapUpCorner;
    }

    public void removeAnimal(Animal animal){
        List<Animal> animalsAtPosition = animalsMap.get(animal.getPosition());
        animalsAtPosition.remove(animal);
    }

    public void removePlant(Plant plant){
        plants.remove(plant.getPosition(),plant);
    }

//    public void eatPlants(AnimalWaiter animalWaiter) {
//
//    }

    public void eatPlantAt(Vector2d position, AnimalWaiter waiter) {
        Plant plant = plants.get(position);
        if (plant == null) return;

        var animals = animalsMap.get(position);
        if (animals == null || animals.isEmpty()) return;

        waiter.feedBest(plant.getEnergy(), animals);

        plants.remove(position);
    }


    public void removeFromPosition(Animal animal, Vector2d position){
        List<Animal> animalsAtPosition = animalsMap.get(position);
        animalsAtPosition.remove(animal);
    }

    public void move(Animal animal){
        Vector2d oldPosition = animal.getPosition();
        animal.move(this);
        Vector2d newPosition = animal.getPosition();
        if (newPosition != oldPosition){
            this.place(animal);
            this.removeFromPosition(animal,oldPosition);
        }
    }

    public Map<Vector2d, List<Animal>> getAnimalsMap(){
        return animalsMap;
    }

    public List<Animal> getAnimalLiveList(){
        return animalsLiveList;
    }

    public List<Animal> getAnimalsDiedList(){
        return animalsDiedList;
    }

    public Map<Vector2d, Plant> getPlants(){
        return plants;
    }
    public String toString(){
        return mapVisualizer.draw(DOWN_CORNER,mapUpCorner);
    }
}
