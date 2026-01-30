package darwin.model.map;

import darwin.model.Vector2d;

public interface MoveValidator {

    boolean canMoveTo(Vector2d position);
}
