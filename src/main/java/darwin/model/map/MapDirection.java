package model.map;

import model.Vector2d;

public enum MapDirection {
    NORTH,
    NORTHEAST,
    EAST,
    SOUTHEAST,
    SOUTH,
    SOUTHWEST,
    WEST,
    NORTHWEST;

    private static final Vector2d[] unitVectors = {
            new Vector2d(0, 1),
            new Vector2d(1, 1),
            new Vector2d(1, 0),
            new Vector2d(1,-1),
            new Vector2d(0, -1),
            new Vector2d(-1, -1),
            new Vector2d(-1,0),
            new Vector2d(-1,1)
    };

    public Vector2d toUnitVector() {
        return unitVectors[this.ordinal()];
    }

    public MapDirection next(){
        int nextOrdinal = (ordinal() + 1) % values().length;
        return values()[nextOrdinal];
    }

    public MapDirection previous(){
        int nextOrdinal = (ordinal() - 1 +values().length) % values().length;
        return values()[nextOrdinal];
    }

    public MapDirection nextSteps(int steps){
        int nextOrdinal = (ordinal() + steps) % values().length;
        return values()[nextOrdinal];
    }

    public MapDirection oppositeDirection(){
        int nexOrdinal = (ordinal() + (values().length/2)) % values().length;
        return values()[nexOrdinal];
    }

    @Override
    public String toString() {
        return switch (this) {
            case NORTH -> "North";
            case NORTHEAST -> "NorthEast";
            case EAST -> "East";
            case SOUTHEAST -> "SouthEast";
            case SOUTH -> "South";
            case SOUTHWEST -> "SouthWest";
            case WEST -> "West";
            case NORTHWEST -> "NorthWest";
        };
    }
}
