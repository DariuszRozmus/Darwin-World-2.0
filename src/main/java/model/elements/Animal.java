package model.elements;

import model.Vector2d;
import model.map.MapDirection;
import model.map.MoveValidator;
import model.map.WorldMap;

import java.util.Queue;
import java.util.UUID;

public abstract class Animal implements WorldElement{


    private MapDirection direction;

    private Vector2d position;

    private int energy;

    private final int birthDay;
    private boolean live = true;
    private boolean hasMoved = false;

    private final UUID uuid = UUID.randomUUID();
    private final WorldMap worldMap;

    private Queue<Gene> genes;

    public Animal (int birthDay, int energy, Queue<Gene> genes,
                   MapDirection direction, Vector2d position, WorldMap worldMap){
        this.birthDay = birthDay;
        this.energy = energy;
        this.direction = direction;
        this.position = position;
        this.genes = genes;
        this.worldMap = worldMap;
    }

    public MapDirection getDirection(){
        return direction;
    }

    public Vector2d getPosition(){
        return position;
    }

    public UUID getUUID() {
        return uuid;
    }

    public int getEnergy(){
        return energy;
    }

    public int getBirthDay(){
        return birthDay;
    }

    public Queue<Gene> getGene(){
        return genes;
    }
//    TODO
//    public Species getSpecies(){
//        return worldMap.getSpeciesAt(position);
//    }
    public boolean isLive(){
        return live;
    }

    public boolean isHasMoved(){
        return hasMoved;
    }

    public void decreaseEnergy(int energy){
        this.energy -= energy;
        live = this.energy > 0;
    }

    public void increaseEnergy(int energy){
        this.energy += energy;
        live = this.energy > 0;
    }

    public boolean isAt(Vector2d position) {
        return this.position.equals(position);
    }

    public void move(MoveValidator moveValidator){
        if(genes.isEmpty()) return;

        Gene gene = genes.poll();
        genes.offer(gene);
        this.direction = direction.nextSteps(gene.getRotation());
        Vector2d newPosition = position.add(direction.toUnitVector());
        if(moveValidator.canMoveTo(newPosition)){
            this.position = newPosition;
            this.hasMoved = true;
        } else {
            this.direction = direction.oppositeDirection();
            this.hasMoved = false;
        }
    }

    @Override
    public int compareTo(WorldElement worldElement){
        if(worldElement instanceof Animal otherAnimal){
            return Integer.compare(otherAnimal.energy, this.energy);
        }
        return 0;
    }

    public String toString(){
        switch (direction){
            case NORTH ->  {return "↑";}
            case EAST ->   {return "→";}
            case SOUTH ->  {return "↓";}
            case WEST ->   {return "←";}
            case NORTHEAST -> {return "↗";}
            case NORTHWEST -> {return "↖";}
            case SOUTHEAST -> {return "↘";}
            case SOUTHWEST -> {return "↙";}
            default -> {return "?";}
        }
    }

}
