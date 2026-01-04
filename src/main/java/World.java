import model.Vector2d;
import model.map.WorldMap;

public class World {
    public static void main() {
        WorldMap worldMap = new WorldMap(new Vector2d(6,6), new Vector2d(2,2),
                new Vector2d(4,4), 5, 5);
    Simulation simulation = new Simulation(worldMap, 2, 20, 3);
    simulation.run();
}}
