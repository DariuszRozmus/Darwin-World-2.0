package darwin.model.elements;

import darwin.model.Vector2d;

import java.util.UUID;

public interface WorldElement extends Comparable<WorldElement> {

    Vector2d getPosition();

    UUID getUUID();

    int compareTo(WorldElement worldElement);
}
