package model.elements;

import model.Vector2d;

import java.util.UUID;

public interface WorldElement extends Comparable{

    Vector2d getPosition();

    UUID getUUID();

    @Override
    int compareTo(Object o);
}
