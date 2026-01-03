package model.elements;

import model.Vector2d;
import model.map.MapDirection;

import java.util.Queue;

public class Herbivore extends Animal {

    public Herbivore(int birthDay, int energy, Queue<Gene> geneList, MapDirection direction, Vector2d position) {
        super(birthDay, energy, geneList, direction, position);
    }
}