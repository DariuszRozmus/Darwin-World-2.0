package model.elements;

import model.Vector2d;

import java.util.UUID;

public interface WorldElement {

    Vector2d getPosition();

    UUID getUUID();
}
