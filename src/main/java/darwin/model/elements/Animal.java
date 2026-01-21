package darwin.model.elements;

import darwin.model.Vector2d;
import darwin.model.map.MapDirection;
import darwin.model.map.MoveValidator;
import darwin.model.map.WorldMap;

import java.util.Queue;
import java.util.UUID;

public class Animal implements WorldElement{


    private MapDirection direction;

    private Vector2d position;

    private int energy;

    private final int birthDay;
    private boolean live = true;
    private int deathDay = -1;
    private int childrenCount = 0;
    private boolean hasMoved = false;
    private final Species specie;

    private final UUID uuid = UUID.randomUUID();
    private final WorldMap worldMap;

    private Queue<Gene> genes;

    public Animal (int birthDay, int energy, Species specie, Queue<Gene> genes,
                   MapDirection direction, Vector2d position, WorldMap worldMap){
        this.birthDay = birthDay;
        this.energy = energy;
        this.specie = specie;
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

    public Species getSpecie(){
        return specie;
    }

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

    public void increaseChildrenCount(){
         this.childrenCount +=1;
    }

    public void setDeathDay(int deathDay){
        this.deathDay = deathDay;
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
