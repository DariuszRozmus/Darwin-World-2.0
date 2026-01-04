package model.elements;

import model.Vector2d;
import model.map.MapDirection;
import model.map.MoveValidator;

import java.util.List;
import java.util.Queue;
import java.util.UUID;

import static java.lang.Math.random;

public abstract class Animal implements WorldElement{


    private MapDirection direction;

    private Vector2d position;

    private int energy;

    private final int birthDay;

    private final UUID uuid = UUID.randomUUID();

    private Queue<Gene> geneList;

    public Animal (int birthDay, int energy, Queue<Gene> geneList,
                   MapDirection direction, Vector2d position){
        this.birthDay = birthDay;
        this.energy = energy;
        this.direction = direction;
        this.position = position;
        this.geneList = geneList;
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

    public boolean isAt(Vector2d position) {
        return this.position.equals(position);
    }

    public void move(MoveValidator moveValidator){
        if(geneList.isEmpty()) return;

        Gene gene = geneList.poll();
        geneList.offer(gene);
        Vector2d newPosition = position.add(direction.toUnitVector());
        if(moveValidator.canMoveTo(position)){
            this.direction = direction.nextSteps(gene.getRotation());
            this.position = newPosition;
        } else {
            this.direction = direction.oppositeDirection();
        }
    }

    public String toString(){
        return "@";
    }

}
