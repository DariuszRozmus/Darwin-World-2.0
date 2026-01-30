package darwin.model.map;

import darwin.model.Vector2d;

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

    public static MapDirection fromVector(Vector2d vector) {
        int x = vector.getX();
        int y = vector.getY();

        if (x == 0 && y == 0) return MapDirection.NORTH;
        if (x == 0 && y > 0) return MapDirection.NORTH;
        if (x == 0 && y < 0) return MapDirection.SOUTH;

        if (x > 0 && y > 0) return MapDirection.NORTHEAST;
        if (x > 0 && y < 0) return MapDirection.SOUTHEAST;
        if (x > 0 && y == 0) return MapDirection.EAST;

        if (x < 0 && y > 0) return MapDirection.NORTHWEST;
        if (x < 0 && y < 0) return MapDirection.SOUTHWEST;
        if (x < 0 && y == 0) return MapDirection.WEST;

        throw new IllegalArgumentException("Zero vector has no direction");
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
