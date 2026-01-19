package model.elements;

import model.Vector2d;

public class Grass extends Plant{
    public Grass(Vector2d position, int energy) {
        super(position, energy);
    }

    public String toString(){
        return "*";
    }

    @Override
    public int compareTo(WorldElement worldElement) {
        return 0;
    }
}
