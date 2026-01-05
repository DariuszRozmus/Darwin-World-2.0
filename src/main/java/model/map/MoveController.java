package model.map;

import model.Vector2d;

public class MoveController implements MoveValidator{
    @Override
    public boolean canMoveTo(Vector2d position) {
        return false;
    }
}
