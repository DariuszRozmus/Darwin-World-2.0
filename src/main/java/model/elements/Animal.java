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

    private Queue<Gene> geneList;

    public Animal (int birthDay, int energy, Queue<Gene> geneList,
                   MapDirection direction, Vector2d position, WorldMap worldMap){
        this.birthDay = birthDay;
        this.energy = energy;
        this.direction = direction;
        this.position = position;
        this.geneList = geneList;
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

    public boolean isLive(){
        return live;
    }

    public boolean isHasMoved(){
        return hasMoved;
    }

    public void decreaseEnergy(int energy){
        this.energy -= energy;
        live = this.energy > 0;
//        if(!live){
//            this.died();
//        }
    }

//    public void died(){
//        worldMap.killAnimal(this);
//    }

    public boolean isAt(Vector2d position) {
        return this.position.equals(position);
    }

    public void move(MoveValidator moveValidator){
        if(geneList.isEmpty()) return;

        Gene gene = geneList.poll();
        geneList.offer(gene);
        Vector2d newPosition = position.add(direction.toUnitVector());
        if(moveValidator.canMoveTo(newPosition)){
            this.direction = direction.nextSteps(gene.getRotation());
            this.position = newPosition;
            this.hasMoved = true;
        } else {
            this.direction = direction.oppositeDirection();
        }
    }

    public int compareTo(Object obj){return 0;}

    public String toString(){
        return "@";
    }

}
