package model.elements;

import model.Vector2d;

import java.util.UUID;

public abstract class Plant implements WorldElement{

    private Vector2d position;

    private int energy;

    private final UUID uuid = UUID.randomUUID();

    public Plant(Vector2d position, int energy){
        this.position = position;
        this.energy = energy;
    }

    public Vector2d getPosition(){
        return position;
    }

    public UUID getUUID() {
        return uuid;
    }

    public void grow(int energy){
        this.energy += energy;
    }
}
