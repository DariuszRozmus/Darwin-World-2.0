package model.map;

import model.Vector2d;
import model.elements.Animal;
import model.elements.Grass;
import model.elements.Plant;
import model.elements.WorldElement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public WorldMap(Vector2d mapUpCorner, Vector2d jungleDownCorner, Vector2d jungleUpCorner,
                    int junglePlant, int mapPlant){
        this.mapUpCorner = mapUpCorner;
        this.jungleDownCorner = jungleDownCorner;
        this.jungleUpCorner = jungleUpCorner;
        this.junglePlant = junglePlant;
        this.mapPlant = mapPlant;
        this.mapVisualizer = new MapVisualizer(this);
        
        int mapWidth = mapUpCorner.getX();
        int mapHeight = mapUpCorner.getY();
        RandomPositionGenerator mapRandomPositionGenerator =
                new RandomPositionGenerator(mapWidth, mapHeight, mapPlant);
        mapRandomPositionGenerator.iterator()
                .forEachRemaining(vector2d -> plants.put(vector2d,new Grass(vector2d,20)));

        int jungleWidth = jungleUpCorner.subtract(jungleDownCorner).getX();
        int jungleHeight = jungleUpCorner.subtract(jungleDownCorner).getY();
        RandomPositionGenerator jungleRandomPositionGenerator =
                new RandomPositionGenerator(jungleWidth, jungleHeight, junglePlant);
        jungleRandomPositionGenerator.iterator()
                .forEachRemaining(vector2d -> plants.put(vector2d.add(jungleDownCorner),new Grass(vector2d,20)));
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

    public void killAnimal(Animal animal){
//        animalLiveList.remove(animal);
        this.removeAnimal(animal);
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
    public String toString(){
        return mapVisualizer.draw(DOWN_CORNER,mapUpCorner);
    }
}
